import net.msrandom.minecraftcodev.runs.MinecraftRunConfiguration

plugins {
	id("earth.terrarium.cloche") version "0.18.10"
}

repositories {
	cloche.librariesMinecraft()

	mavenCentral()

	cloche {
		main()

		mavenFabric()
		mavenNeoforgedMeta()
		mavenNeoforged()
		mavenParchment()
	}

	// this one's broken for some reason. probably switch back once able?
//	maven {
//		// EMI & Modmenu
//		name = "TerraformersMC"
//		setUrl("https://maven.terraformersmc.com/")
//	}
	maven("https://api.modrinth.com/maven") { name = "Modrinth" }
	maven(url = "https://maven.createmod.net/") { name = "Create" }
}

cloche {
//	val emiVersion = "1.1.22+1.21.1"

	minecraftVersion = "1.21.1"
	metadata {
		modId = "deselect"
		name = "Deselect"
		license = "GPL-3.0"
		description = "Press a button to empty your hand without messing with your hotbar!"
		icon = "assets/deselect/icon.png"

		author("jolkert")
	}

	mappings {
		official()
		parchment("2024.11.17")
	}

	common("common") {

	}

	neoforge {
		loaderVersion = "21.1.135"
		metadata {
			mixins.from("src/common/deselect.mixins.json")
			mixins.from("src/neoforge/compat.deselect.mixins.json")
		}


		dependencies {
//			modRuntimeOnly("dev.emi:emi-neoforge:${emiVersion}")
//			modRuntimeOnly("maven.modrinth:fRiHVvU7:5sIPA1To") // EMI 1.1.24+1.21.1-neoforge
			modCompileOnly("com.simibubi.create:create-1.21.1:6.0.10-281")
		}

		runs {
			server()
			client {
				setUsernameAndUuid()
			}
		}
	}

//	fabric {
//		loaderVersion = "0.16.10"
//		metadata {
//			entrypoint("main", "dev.jolkert.deselect.fabric.DeselectFabric")
//			entrypoint("client", "dev.jolkert.deselect.fabric.client.DeselectFabricClient")
//
//			dependency("minecraft", minecraftVersion.get())
//			dependency {
//				modId = "fabric"
//			}
//
//			mixins.from("src/common/deselect.mixins.json")
//		}
//		data()
//		includedClient()
//
////		client {
////			tasks.named<Jar>(sourceSet.jarTaskName) {
////				duplicatesStrategy = DuplicatesStrategy.INCLUDE
////			}
////		}
//
//		dependencies {
//			fabricApi("0.115.2")
////			dependencies {
//////				modRuntimeOnly("dev.emi:emi-fabric:${emiVersion}")
//////				modRuntimeOnly("maven.modrinth:fRiHVvU7:on5GT1qh") // EMI 1.1.24+1.21.1-fabric
//////			 	modRuntimeOnly("maven.modrinth:mOgUt4GM:v6Xx3fbU") // Mod Menu 11.0.4
////			}
//		}
//
//		runs {
//			server()
//			client {
//				setUsernameAndUuid()
//			}
//			data()
//		}
//	}
}

fun MinecraftRunConfiguration.setUsernameAndUuid()
{
	val username = System.getenv("MC_USER")
	if (!username.isNullOrBlank())
	{
		args("--username", username)
	}

	val uuid = System.getenv("MC_UUID")
	if (!uuid.isNullOrBlank())
	{
		args("--uuid", uuid)
	}
}
