import java.io.File

tasks {
    register<VersionIncrement>("incrementRelease") {
        segment.set(VersionSegment.RELEASE)
    }

    register<VersionIncrement>("incrementFeature") {
        segment.set(VersionSegment.FEATURE)
    }

    register<VersionIncrement>("incrementFix") {
        segment.set(VersionSegment.FIX)
    }
}

abstract class VersionIncrement @javax.inject.Inject constructor(): DefaultTask() {
    init {
        group = "versioning"
        description = "Increments version segment"
    }

    @get:Input
    abstract val segment: Property<VersionSegment>

    @TaskAction
    fun run() {
        val propertiesFile = File(PROPERTIES_FILE_PATH)
        val properties = propertiesFile.readProperties()

        val versionProperties = VersionProperties.wrap(properties)
        val currentVersion = versionProperties.toVersion()

        val newVersion = when (segment.get()) {
            VersionSegment.RELEASE -> currentVersion.incrementRelease()
            VersionSegment.FEATURE -> currentVersion.incrementFeature()
            VersionSegment.FIX -> currentVersion.incrementFix()
        }

        versionProperties.applyVersion(newVersion)
        propertiesFile.writeProperties(versionProperties.properties)
    }

    private fun File.readProperties(): java.util.Properties {
        val properties = java.util.Properties()

        inputStream().use {
            properties.load(it)
        }

        return properties
    }

    private fun File.writeProperties(properties: java.util.Properties) {
        writer().use {
            properties.store(it, null)
        }
    }

    private companion object {
        const val PROPERTIES_FILE_PATH = "version.properties"
    }
}

enum class VersionSegment {
    RELEASE,
    FEATURE,
    FIX
}

class VersionProperties private constructor(val properties: java.util.Properties) {
    companion object {
        fun wrap(properties: java.util.Properties) =
                VersionProperties(properties)

        private object PropertyKeys {
            const val RELEASE = "version.release"
            const val FEATURE = "version.feature"
            const val FIX = "version.fix"
            const val CLASSIFIER = "version.classifier"
            const val CODE = "version.code"
            const val NAME = "version.name"
        }
    }

    fun applyVersion(version: Version) {
        with(properties) {
            setProperty(PropertyKeys.RELEASE, version.release.toString())
            setProperty(PropertyKeys.FEATURE, version.feature.toString())
            setProperty(PropertyKeys.FIX, version.fix.toString())
            setProperty(PropertyKeys.CLASSIFIER, version.classifier)
            setProperty(PropertyKeys.CODE, version.toCode())
            setProperty(PropertyKeys.NAME, version.toName())
        }
    }

    fun toVersion(): Version = with(properties) {
        Version(
                release = readInt(PropertyKeys.RELEASE),
                feature = readInt(PropertyKeys.FEATURE),
                fix = readInt(PropertyKeys.FIX),
                classifier = readNonEmptyOrNull(PropertyKeys.CLASSIFIER)
        )
    }

    private fun java.util.Properties.readInt(key: String): Int =
            getProperty(key).toInt()

    private fun java.util.Properties.readNonEmptyOrNull(key: String): String? {
        val value: String = getProperty(key)

        return if (value.isEmpty()) null else value
    }
}

data class Version(
        val release: Int,
        val feature: Int,
        val fix: Int,
        val classifier: String?
) {
    fun incrementRelease(): Version = copy(
            release = release + 1,
            feature = 0,
            fix = 0
    )

    fun incrementFeature(): Version = copy(
            feature = feature + 1
    )

    fun incrementFix(): Version = copy(
            fix = fix + 1
    )

    fun toCode(): String =
            release.toString().padStart(2, '0') +
                    feature.toString().padStart(2, '0') +
                    fix.toString().padStart(2, '0') +
                    "00" // Reserved

    fun toName(): String =
            "$release.$feature.$fix" + if (classifier != null) "-$classifier" else ""
}
