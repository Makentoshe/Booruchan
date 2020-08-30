package vcs

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object BooruchanVcsRoot : GitVcsRoot({
    name = "Github"
    url = "git@github.com:Makentoshe/Booruchan2.git"
    authMethod = uploadedKey { uploadedKey = "id_rsa" }
})
