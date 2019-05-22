workspace(name = "yggdrasil")

load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

bazel_version="0.25.2"
bazel_sha256="21cbd027077d95310752e9e45c4242c26067a01fd838e706c856706a8668c866"

http_archive(
    name = "bazel",
    strip_prefix = "bazel-%s" % bazel_version,
    type = "zip",
    url = "https://github.com/bazelbuild/bazel/archive/%s.zip" % bazel_version,
    sha256 = bazel_sha256,
    patch_cmds = [
                  """find ./ -type f -name BUILD | xargs sed -i.bak -e 's/visibility = \[[^]]*\]/visibility = \["\/\/visibility:public"\]/g'"""
                  ],
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "9510dd2afc29e7245e9e884336f848c8a6600a14ae726adb6befdb4f786f0be2",
    urls = ["https://github.com/protocolbuffers/protobuf/archive/v3.6.1.3.zip"],
    strip_prefix = "protobuf-3.6.1.3",
)

local_repository(
    name = "rules_scala_annex",
    path = "vendor/rules_scala",
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "515ee5265387b88e4547b34a57393d2bcb1101314bcc5360ec7a482792556f42",
    strip_prefix = "rules_jvm_external-2.1",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/2.1.zip",
)

load("@rules_scala_annex//rules/scala:workspace.bzl", "scala_register_toolchains", "scala_repositories")
scala_repositories()
scala_register_toolchains()

bind(
    name = "default_scala",
    actual = "@rules_scala_annex//src/main/scala:zinc_2_12_8",
)

git_repository(
    name = "bazel_skylib",
    remote = "https://github.com/bazelbuild/bazel-skylib.git",
    tag = "0.6.0",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_repositories = [
    "https://jcenter.bintray.com",
]

maven_install(
    artifacts = [
        "io.circe:circe-core_2.12:0.10.0",
        "io.circe:circe-yaml_2.12:0.10.0",
        "io.grpc:grpc-netty:jar:1.20.0",
        "io.monix:minitest_2.12:jar:2.4.0",
        "org.neo4j.driver:neo4j-java-driver:jar:1.7.4",
        "org.neo4j.test:neo4j-harness:jar:3.5.5",
        "org.neo4j:neo4j:jar:3.5.5",
        "org.scalaz:scalaz-zio-interop-cats_2.12:jar:0.19",
        "org.scalaz:scalaz-zio_2.12:jar:0.19",
        "org.typelevel:cats-core_2.12:jar:1.6.0",
        "org.typelevel:cats-effect_2.12:jar:1.3.0",
    ],
    repositories = maven_repositories,
)

maven_install(
    name = "maven_neo4j",
    artifacts = [
        "org.neo4j:neo4j:jar:3.5.5",
    ],
    repositories = maven_repositories,
)

"""
git_repository(
    name = "io_grpc_grpc_java",
    remote = "https://github.com/grpc/grpc-java",
    tag = "v1.20.0",
)
"""
