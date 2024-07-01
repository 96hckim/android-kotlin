plugins {
    `kotlin-dsl`
}

group = "com.hocheol.hiltsns.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

gradlePlugin {
    plugins {
        register("snsHiltPlugin") {
            id = "sns.hilt.plugin"
            implementationClass = "SNSHiltPlugin"
        }
    }
}