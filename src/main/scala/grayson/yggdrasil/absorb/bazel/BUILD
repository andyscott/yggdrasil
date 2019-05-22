scala_binary(
    name = "bazel",
    srcs = glob([
        "*.scala",
    ]),
    deps = [
    	"//src/main/scala/grayson/yggdrasil/prelude",
        "@maven//:org_typelevel_cats_core_2_12",
	"@maven//:org_typelevel_cats_kernel_2_12",
	"@maven//:org_typelevel_cats_effect_2_12",
	#"@maven//:org_neo4j_driver_neo4j_java_driver",
	"@maven//:org_neo4j_neo4j",
	"@maven//:org_neo4j_neo4j_graphdb_api",
        "@bazel//src/main/protobuf:build_java_proto",
	"@maven//:io_verizon_quiver_core_2_12",
    ],
)