rootProject.name = "Booruchan"

include("core")

include("danbooru")
include("danbooru:network-check")

include("gelbooru")
include("gelbooru:network-check")

include("application")

include("application:android")
include(":application:android:app")
