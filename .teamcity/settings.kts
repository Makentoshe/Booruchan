import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.version

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.1"

project {
    vcsRoot(BooruchanVcsRoot)
//    buildType(InternalBuild)

    buildType(Core)
    buildType(Gelbooru)
    buildType(Danbooru)
}

object Core : BuildType({
    name = "Core"

    publishArtifacts = PublishMode.ALWAYS
    artifactRules = "./booruchan-core/build/libs/* => core"

    steps {
        gradle {
            name = "$name module build"
            tasks = "booruchan-core:build"
            buildFile = "build.gradle.kts"
        }
    }

    vcs {
        root(BooruchanVcsRoot)
    }

    // Triggers are used to add builds to the queue either when an event occurs (like a VCS check-in)
    // or periodically with some configurable interval
    triggers { vcs { } }
})

object Gelbooru : BuildType({
    name = "Gelbooru"

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => ./booruchan-gelbooru/libs"
        }
    }

    steps {
        gradle {
            name = "$name module build"
            tasks = ":booruchan-gelbooru:build"
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
    }

    vcs {
        root(BooruchanVcsRoot)
    }

    // Triggers are used to add builds to the queue either when an event occurs (like a VCS check-in)
    // or periodically with some configurable interval
    triggers { vcs { } }
})

object Danbooru : BuildType({
    name = "Danbooru"

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => ./booruchan-danbooru/libs"
        }
    }

    steps {
        gradle {
            name = "$name module build"
            tasks = ":booruchan-danbooru:build"
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
    }

    vcs {
        root(BooruchanVcsRoot)
    }

    // Triggers are used to add builds to the queue either when an event occurs (like a VCS check-in)
    // or periodically with some configurable interval
    triggers { vcs { } }

})

object InternalBuild : BuildType({
    name = "Internal"
    id("internal")

    publishArtifacts = PublishMode.ALWAYS
    artifactRules = "./booruchan-core/build/libs/* => jars"

    vcs {
        root(BooruchanVcsRoot)
    }

    // Triggers are used to add builds to the queue either when an event occurs (like a VCS check-in)
    // or periodically with some configurable interval
    triggers { vcs { } }

    steps {
        val coreJarDirectory = "./booruchan-core/build/libs"
        val gelbooruLibDirectory = "./booruchan-gelbooru/libs"

        gradle {
            name = "Core module build"
            tasks = "booruchan-core:build"
            buildFile = "build.gradle.kts"
        }
        script {
            name = "Delivery core artifacts to dependent modules"
            scriptContent = """
                rm -rf $gelbooruLibDirectory
                mkdir $gelbooruLibDirectory
                cp -a $coreJarDirectory/. $gelbooruLibDirectory
            """.trimIndent()
        }
        gradle {
            name = "Gelbooru module build"
            tasks = ":booruchan-gelbooru:build"
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
        script {
            name = "Debug check"
            scriptContent = "ls -R $coreJarDirectory"
        }
    }

//    // A build feature is a piece of functionality that can affect a build process or reporting its results.
//    features {
//        pullRequests {
//            vcsRootExtId = "${DslContext.settingsRoot.id}"
//            provider = github {
//                authType = token {
//                    token = "credentialsJSON:48aba3a6-9f18-45d4-9d8d-ec3744e9f450"
//                }
//                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
//            }
//        }
//    }
})

object BooruchanVcsRoot : GitVcsRoot({
    name = "Github"
    url = "git@github.com:Makentoshe/Booruchan2.git"
    authMethod = uploadedKey { uploadedKey = "id_rsa" }
})