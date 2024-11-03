enablePlugins(IntegrationTestsPlugin, sbtdocker.DockerPlugin)

description := "NODE integration tests"
libraryDependencies ++= Dependencies.it

inTask(docker)(
  Seq(
    imageNames   := Seq(ImageName("cardiumnetwork/node-it")),
    dockerfile   := NativeDockerfile(baseDirectory.value.getParentFile / "docker" / "Dockerfile"),
    buildOptions := BuildOptions()
  )
)

val packageAll = taskKey[Unit]("build all packages")
docker := docker.dependsOn(LocalProject("waves-node") / packageAll).value
