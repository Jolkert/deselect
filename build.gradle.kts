import net.msrandom.minecraftcodev.runs.MinecraftRunConfiguration
// ig cloche is busted (https://github.com/terrarium-earth/cloche/issues/157) -morgan 2026-07-29
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

plugins {
	id("earth.terrarium.cloche") version "0.19.11"
}

group = "dev.jolkert"
version = "2.0.3"

repositories {
	cloche.main()
	cloche.librariesMinecraft()
	mavenCentral()
	cloche {
		mavenFabric()
		mavenNeoforgedMeta()
		mavenNeoforged()
		mavenParchment()
	}

	maven("https://maven.terraformersmc.com/") { name = "Terraformers" } // Mod Menu
	maven("https://maven.createmod.net/") { name = "Create" }
}

cloche {
	metadata {
		modId = "deselect"
		name = "Deselect"
		description = "Press a button to empty your hand without messing with your hotbar!"
		icon = "assets/deselect/icon.png"
		author("jolkert")
		license = "GPL-3.0"
	}

	common {
		metadata {
			// cross-version mixins is probably ill-advised? but dammit we're gonna do it anyways
			// -morgan 2026-07-29
			mixins.from("src/common/main/deselect.common.mixins.json")
		}
	}

	val latestVersion = "26.2" // Fabric Only
	val ltsVersion = "1.21.1" // Fabric & Neoforge

	val ltsCommon = common("common:$ltsVersion") {
		metadata {
			mixins.from("src/common/1.21.1/main/deselect.common-1.21.1.mixins.json")
		}
	}

	// ------------------------------------------
	// | FABRIC (https://fabricmc.net/develop/) |
	// ------------------------------------------
	fabric("fabric:$latestVersion") {
		minecraftVersion = latestVersion
		loaderVersion = "0.19.3"
		includedClient()

		metadata {
			entrypoint("main", "dev.jolkert.deselect.fabric.DeselectFabricLatest")
			entrypoint("client", "dev.jolkert.deselect.fabric.client.DeselectClientFabricLatest")
			mixins.from("src/fabric/26.2/main/deselect.fabric-26.2.mixins.json")

			dependency {
				modId = "fabric-api"
			}
		}

		val modMenuVersion = "20.0.1"
		dependencies {
			fabricApi("0.156.0")
			runtimeOnly("com.terraformersmc:modmenu:$modMenuVersion")
		}

		runs {
			server()
			client() {
				setUsernameAndUuid()
			}
		}
	}

	fabric("fabric:$ltsVersion") {
		dependsOn(ltsCommon)
		minecraftVersion = ltsVersion
		loaderVersion = "0.19.3"
		includedClient()
		mappings {
			official()
			parchment("2024.11.17") // https://parchmentmc.org/docs/getting-started.html
		}

		metadata {
			entrypoint("main", "dev.jolkert.deselect.fabric.DeselectFabricLts")
			entrypoint("client", "dev.jolkert.deselect.fabric.client.DeselectClientFabricLts")
			dependency {
				modId = "fabric"
			}
		}

		val modMenuVersion = "11.0.4"
		dependencies {
			fabricApi("0.116.15")
			modImplementation("com.terraformersmc:modmenu:$modMenuVersion")
		}

		runs {
			server()
			client() {
				setUsernameAndUuid()
			}
		}

	}

	// -------------------------------------
	// | NEOFORGE (https://neoforged.net/) |
	// -------------------------------------
	neoforge {
		dependsOn(ltsCommon)
		minecraftVersion = ltsVersion
		loaderVersion = "21.1.244"
		mappings {
			official()
			parchment("2024.11.17") // https://parchmentmc.org/docs/getting-started.html
		}

		metadata {
			mixins.from("src/neoforge/main/deselect.create-compat.mixins.json")
		}

		dependencies {
			modCompileOnly("com.simibubi.create:create-1.21.1:6.0.10-281")
		}

		runs {
			server()
			client() {
				setUsernameAndUuid()
			}
		}
	}
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
