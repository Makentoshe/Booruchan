package com.makentoshe.imageview.download

import com.makentoshe.api.ComposeDownloadListener
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.SimpleComposeDownloadListener
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.api.cache.ImageDiskCache
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.DownloadListener
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import java.io.File
import java.io.Serializable


class CustomDownloadExecutor(
    private val permissionexecutor: PermissionExecutor,
    private val notificationExecutor: NotificationExecutor,
    private val downloadfolder: File,
    private val repositoryBuilder: RepositoryBuilder,
    private val disposables: CompositeDisposable
) : DownloadExecutor, Serializable {

    /** Write external storage permission */
    private val externalstorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    /** File images will be "cached" (saved) in [downloadfolder] */
    private val cache = ImageDiskCache(downloadfolder) { it.id.toString().plus(".jpg") }

    /** Error event emitter */
    private val errorSubject = AsyncSubject.create<Throwable>()

    /** Success event emitter */
    private val successSubject = AsyncSubject.create<ByteArray>()

    override fun download(post: Post, callback: (Throwable?) -> Unit) {
        // send callback error
        errorSubject.observeOn(AndroidSchedulers.mainThread()).subscribe(callback).let(disposables::add)
        // send callback success
        successSubject.observeOn(AndroidSchedulers.mainThread()).subscribe { callback(null) }.let(disposables::add)
        //request write external storage permission
        permissionexecutor.request(externalstorage) { isAccess ->
            // if access was granted
            if (isAccess) performDownloading(post) else println("not download")
        }
    }

    /** Performs downloading. Result will be placed in [successSubject] or [errorSubject] */
    private fun performDownloading(post: Post) {
        val networkExecutor = getNetworkExecutor(post)
        val repository = repositoryBuilder.buildFileRepository(networkExecutor).wrapCache(cache)
        Single.just(post).observeOn(Schedulers.io()).map(repository::get).subscribe { b, t ->
            // send data to observers and complete
            if (t != null || b == null) errorSubject.onNext(t ?: Exception("wtf")) else successSubject.onNext(b)
            errorSubject.onComplete(); successSubject.onComplete()
        }.let(disposables::add)
    }

    /** Returns a network executor used in downloading process */
    private fun getNetworkExecutor(post: Post): NetworkExecutor {
        val streamListener = getStreamDownloadListener(post)
        val proxyListener = getProxyDownloadListener(post, streamListener)
        return NetworkExecutorBuilder.buildSmartGet(proxyListener, streamListener)
    }

    /** Returns a stream download listener */
    private fun getStreamDownloadListener(post: Post): StreamDownloadListener = SimpleStreamDownloadListener().apply {
        var count = 0f
        onDownloading { bytes, total ->
            count += bytes.size
            val message = "$count/$total"
            val progress = (count / total * 100).toInt()
            notificationExecutor.notifyProgress(post.id, post.id.toString(), message, progress)
        }

        onSuccess {
            val message = "Success"
            notificationExecutor.notifySuccess(post.id, post.id.toString(), message, it.bytes)
        }

        onError {
            notificationExecutor.notifyError(post.id, post.id.toString(), it.error ?: Throwable("wtf"))
        }
    }

    private fun getProxyDownloadListener(
        post: Post, listener: DownloadListener? = null
    ): ComposeDownloadListener<DownloadListener> = SimpleComposeDownloadListener(listener).apply {
        onStart { _, _ ->
            val message = "Connection reset. Switched to a proxied connection"
            notificationExecutor.notifyProgress(post.id, post.id.toString(), message, -1)
        }
        onSuccess {
            val message = "Success"
            notificationExecutor.notifySuccess(post.id, post.id.toString(), message, it.bytes)
        }
        onError {
            notificationExecutor.notifyError(post.id, post.id.toString(), it.error ?: Throwable("wtf"))
        }
    }

}