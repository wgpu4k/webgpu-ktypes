package io.ygdrasil.wgsl.back

import io.ygdrasil.wgsl.valid.Capabilities
import io.ygdrasil.wgsl.valid.ShaderStages
import io.ygdrasil.wgsl.valid.ValidationFlags

/**
 * Options communes à tous les backends.
 */
abstract class BackendOptions {
    /**
     * Flags de validation à appliquer avant la génération.
     */
    abstract val validationFlags: ValidationFlags

    /**
     * Capacités supportées par le backend.
     */
    abstract val capabilities: Capabilities

    /**
     * Étapes de shader supportées.
     */
    abstract val shaderStages: ShaderStages

    /**
     * Indentation à utiliser.
     */
    abstract val indent: String

    /**
     * Saut de ligne.
     */
    abstract val newline: String

    /**
     * Version du langage cible (ex: "450" pour GLSL, "2.3" pour MSL).
     */
    abstract val version: String?

    /**
     * Nom du langage cible.
     */
    abstract val languageName: String

    /**
     * Extension du fichier (ex: ".metal", ".hlsl", ".glsl", ".wgsl").
     */
    abstract val fileExtension: String

    /**
     * Map des ressources.
     */
    abstract val bindingMap: BindingMap
}

/**
 * Options spécifiques à MSL.
 */
data class MslOptions(
    override val validationFlags: ValidationFlags = ValidationFlags.ALL,
    override val capabilities: Capabilities = Capabilities.NONE,
    override val shaderStages: ShaderStages = ShaderStages.ALL,
    override val indent: String = "    ",
    override val newline: String = "\n",
    override val version: String? = "2.3",
    override val languageName: String = "MSL",
    override val fileExtension: String = ".metal",
    override val bindingMap: BindingMap = BindingMap(),

    // Options spécifiques MSL
    val inlineSamplers: Boolean = false,
    val bufferSizeAlignment: Int = 16
) : BackendOptions()

/**
 * Options spécifiques à HLSL.
 */
data class HlslOptions(
    override val validationFlags: ValidationFlags = ValidationFlags.ALL,
    override val capabilities: Capabilities = Capabilities.NONE,
    override val shaderStages: ShaderStages = ShaderStages.ALL,
    override val indent: String = "    ",
    override val newline: String = "\n",
    override val version: String? = "6.0",
    override val languageName: String = "HLSL",
    override val fileExtension: String = ".hlsl",
    override val bindingMap: BindingMap = BindingMap(),

    // Options spécifiques HLSL
    val shaderModel: ShaderModel = ShaderModel.SM6_0
) : BackendOptions()

/**
 * Options spécifiques à GLSL.
 */
data class GlslOptions(
    override val validationFlags: ValidationFlags = ValidationFlags.ALL,
    override val capabilities: Capabilities = Capabilities.NONE,
    override val shaderStages: ShaderStages = ShaderStages.ALL,
    override val indent: String = "    ",
    override val newline: String = "\n",
    override val version: String? = "450",
    override val languageName: String = "GLSL",
    override val fileExtension: String = ".glsl",
    override val bindingMap: BindingMap = BindingMap(),

    // Options spécifiques GLSL
    val profile: GlslProfile = GlslProfile.CORE,
    val es: Boolean = false
) : BackendOptions()

/**
 * Options spécifiques à WGSL.
 */
data class WgslOptions(
    override val validationFlags: ValidationFlags = ValidationFlags.ALL,
    override val capabilities: Capabilities = Capabilities.ALL,
    override val shaderStages: ShaderStages = ShaderStages.ALL,
    override val indent: String = "    ",
    override val newline: String = "\n",
    override val version: String? = null,
    override val languageName: String = "WGSL",
    override val fileExtension: String = ".wgsl",
    override val bindingMap: BindingMap = BindingMap(),

    // Options spécifiques WGSL
    val debug: Boolean = false,
    val prettyPrint: Boolean = true
) : BackendOptions()

/**
 * Options spécifiques à l'IR.
 */
data class IrOptions(
    override val validationFlags: ValidationFlags = ValidationFlags.ALL,
    override val capabilities: Capabilities = Capabilities.ALL,
    override val shaderStages: ShaderStages = ShaderStages.ALL,
    override val indent: String = "    ",
    override val newline: String = "\n",
    override val version: String? = null,
    override val languageName: String = "IR",
    override val fileExtension: String = ".json",
    override val bindingMap: BindingMap = BindingMap()
) : BackendOptions()

/**
 * Modèle de shader pour HLSL.
 */
enum class ShaderModel {
    SM5_0, SM5_1, SM6_0, SM6_1, SM6_2, SM6_3, SM6_4, SM6_5, SM6_6
}

/**
 * Profil GLSL.
 */
enum class GlslProfile {
    CORE, ES
}
