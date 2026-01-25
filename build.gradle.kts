plugins {
	id("earth.terrarium.cloche") version "0.17.7"
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

	maven {
		// EMI & Modmenu
		name = "TerraformersMC"
		setUrl("https://maven.terraformersmc.com/")
	}
}

cloche {
	val emiVersion = "1.1.22+1.21.1"

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

	common {

	}

	neoforge {
		loaderVersion = "21.1.135"
		metadata {
			mixins.from("src/common/deselect.mixins.json")
		}

		data()

		dependencies {
			modRuntimeOnly("dev.emi:emi-neoforge:${emiVersion}")
		}

		runs {
			server()
			client()
			data()
		}
	}

	fabric {
		loaderVersion = "0.16.10"
		metadata {
			entrypoint("main", "dev.jolkert.deselect.fabric.DeselectFabric")
			entrypoint("client", "dev.jolkert.deselect.fabric.client.DeselectFabricClient")

			dependency("minecraft", minecraftVersion.get())
			dependency {
				modId = "fabric"
			}

			mixins.from("src/common/deselect.mixins.json")
		}

		data()
		client {
			tasks.named<Jar>(sourceSet.jarTaskName) {
				duplicatesStrategy = DuplicatesStrategy.INCLUDE
			}
		}

		dependencies {
			fabricApi("0.115.2")
			dependencies {
				modRuntimeOnly("dev.emi:emi-fabric:${emiVersion}")
				modRuntimeOnly("com.terraformersmc:modmenu:11.0.3")
			}
		}

		runs {
			server()
			client()
			data()
		}
	}
}
