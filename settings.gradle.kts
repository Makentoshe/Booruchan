rootProject.name = "Booruchan"
include("core")
include("gelbooru")
include("danbooru")

include("danbooru:network-check")
findProject(":danbooru:network-check")?.name = "network-check"
include("gelbooru:network-check")
findProject(":gelbooru:network-check")?.name = "network-check"
