load("@rules_scala_annex//rules:scala.bzl", "scala_binary")

scala_binary(
    name = "bazel",
    srcs = glob([
        "*.scala",
    ]),
	deps = [
        "@bazel//src/main/protobuf:build_java_proto",
    ],
)
