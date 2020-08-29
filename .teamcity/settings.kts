import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.add
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

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
    buildType(InternalBuild)
}

object InternalBuild : BuildType({
    name = "Internal"
    id("internal") // не обязательно указывать
    // чому?
    // тимсити сам выведет

    vcs {
        root(BooruchanVcsRoot)
    }

    //    короче ты работай, а я посмотрю и подскажу если что
    //    бля, легко сказать
    //    Я хуй знает че счас дальше делать
    //    А что ты хочешь?
    //            ну пока я буду разбираться с тем, какие шаги мне вообще нужны
    // счас прикол в том, что билд билдит весь проект без зависимостей, и надо как то это исправить. В смысле, билдить по модулям?
    // Да, Типо того и подпихивать нужные жарники в нужные модули, типа снежного кома, чтоб это говно собиралось
    // Отдельный модуль собрать это `grale :module-name:build`. Про jar-ники я уже говорил, скорее всего кеши грэдла тебе похерят эту идею. И нужно тогда явно указывать зависимость
    // вида implementation("my-module.jar"). Но тогда у тебя проект собрать по простому нельзя будет, только каждый модуль по отдельности
    // да, это я знаю. Вот я и хочу как-то это обойти
    // мб я думаю, можно ли добавить для градла аргументы, чтоб он их распарсил и рещил как собирать
    // (да можно. В команд лайне это выглядит как -Pargument=value. В самом грэдле получаешь через rootProject.findParameter или rootProject.getParameter)
    // ну да, можно сделать if (partialBuild) implementation("my-module.jar") else implementation(":my-module")
    //, и сделать через ифы
    // Понял. Ну и как-то управлять dependencies через этот иф, если а то по дефолту если б то модули
    // Да, в этом и мысль
    // Бля, если не знать, как этот коментарий появился, похоже на какую-то шизу
    // У gradlew есть аргументы? Для степа?

    steps {
//        script {
//            name = "Clean before install"
//            executionMode = BuildStep.ExecutionMode.ALWAYS
//            scriptContent = "ls"
//        }
        gradle {
            name = "Core module build"
            tasks = "clean :booruchan-core:build"
            buildFile = "build.gradle"
//            gradleArguments
//            gradleParameters // че-то такое
            // Ок, пойду с ноута гляну, там не отвалился автокомплит. Обидно что отвалился. Вообще, так можно хакатон проекты пилить :)
            // Когда следующий будет? Компанейский через год. Вроде в тимсити что-то хотели, но пока не понятно
            // А то можно реализовать ту доку 13 года по приколу. Как вариант
            // Ну чоч - пойду пробовать запускать на тс эту штуку
        }
        gradle {
            name = "Gelbooru module build"
            tasks = "clean :booruchan-gelbooru:build"
            gradleParams = "-Pargument=value"
        }
    }
})

object BooruchanVcsRoot : GitVcsRoot({
    name = "Github"
    url = "git@github.com:Makentoshe/Booruchan2.git"
    authMethod = uploadedKey { uploadedKey = "id_rsa" }
})
