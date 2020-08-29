import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.project
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
    buildType(InternalBuild)
}

object InternalBuild : BuildType({
    name = "Internal"

    vcs {
        root(GithubVcsRoot)
    }

    steps {
//        script {
//            name = "Clean before install"
//            executionMode = BuildStep.ExecutionMode.ALWAYS
//            scriptContent = "ls"
//        }
        gradle {
            name = "Core module build"
            tasks = ":booruchan-core:build"
            buildFile = "build.gradle"
        }
        script {
            name = "Deliver core binary to dependent modules"
            scriptContent = "ls"
        }
//        gradle {
//            name = "Gelbooru module build"
//            tasks = ":booruchan-gelbooru:build"
//            gradleParams = "-Pmodular"
//        }
    }
})

object GithubVcsRoot : GitVcsRoot({
    name = "Github"
    url = "git@github.com:Makentoshe/Booruchan2.git"
    authMethod = uploadedKey { uploadedKey = "id_rsa" }
})
