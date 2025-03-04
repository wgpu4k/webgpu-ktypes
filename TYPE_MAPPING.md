# WebGPU to Kotlin Type Mapping

This document outlines the comprehensive strategy for mapping WebGPU types to Kotlin Multiplatform Project implementations. 
It serves as a definitive guide for maintaining consistency across different platforms while ensuring type safety and optimal performance. 
The mapping strategy detailed herein focuses on creating platform-agnostic representations of WebGPU types, facilitating seamless integration across various target platforms supported by Kotlin. 
This documentation is essential for developers working on WebGPU implementations in Kotlin, particularly those dealing with cross-platform compatibility challenges.

# Mapping input

The WebGPU specifications referenced in this document are derived from the official website
at [https://www.w3.org/TR/webgpu/](https://www.w3.org/TR/webgpu/), particularly the WebIDL definitions provided at the
end of the webpage. These WebIDL definitions serve as the foundation for creating platform-agnostic type mappings and
ensuring compliance with the WebGPU standard. By leveraging these authoritative specifications, the implementation
remains consistent with the official WebGPU guidelines.

# ArrayBuffer

I would like to gather feedback on this point. Please let me know your thoughts or any suggestions.

# AllowSharedBufferSource

`AllowSharedBufferSource` will be mapped to a `BufferSource` type. This type will be implemented with a companion method
that accepts a list of primitives and memory sources, depending on the platform. The companion method will provide a
consistent and platform-agnostic way to initialize `BufferSource` while ensuring compatibility with the underlying
memory management requirements of the target environment.

# Primitive Type Mapping

Primitives in WebGPU will be mapped to Kotlin primitive types directly, ensuring type safety and performance efficiency.
The mapping follows a platform-agnostic strategy to maintain consistency across all target platforms in a Kotlin
Multiplatform Project. Below is the mapping guideline for the basic types:

- **integers**: WebGPU integer types (e.g., `unsigned long`, `long`) will be directly mapped to Kotlin's `Int` or `UInt`
  types, depending on their signedness.

- **long long integers**: WebGPU `long long` and `unsigned long long` types will be mapped to Kotlin's `Long` or `ULong`
  for handling larger integer values.

- **floating-point numbers**: WebGPU float types (e.g., `float`, `double`) will be mapped to Kotlin's `Float` or
  `Double` for precision.

- **booleans**: The WebGPU `boolean` type will be mapped to Kotlin's `Boolean` type.

- **strings**: WebGPU strings (e.g., `DOMString`) will be mapped to Kotlin's `String`, which is suitable for
  cross-platform usage.

# Excluded Types

The listed types will be ignored because they are either browser-specific, redundant, or relate to specialized web
contexts that are not relevant to the current implementation. Here’s a breakdown of why these types are excluded:

1. **Browser-specific types**:
    - `NavigatorGPU`, `Navigator`, `WorkerNavigator`  
      These types belong to browser APIs and are not part of the core functionality being addressed. Including them
      would unnecessarily couple the code to a browser environment, limiting portability or usage in non-browser
      contexts.

2. **Web canvas-related types**:
    - `GPUCanvasContext`, `GPUCanvasConfiguration`, `GPUCanvasAlphaMode`, `GPUCanvasToneMappingMode`,
      `GPUCanvasToneMapping`  
      These types are primarily used for web-based canvas operations. Given their specialized nature, they are omitted
      unless the implementation specifically targets the WebGPU canvas functionality.

3. **Redundant dictionary types**:
    - `GPUColorDict`, `GPUOrigin2DDict`, `GPUOrigin3DDict`, `GPUExtent3DDict`  
      These are dictionary types that are repetitive in structure or purpose, and their inclusion might lead to
      verbosity without significant gains. Simplified or consolidated representations may already suffice.

4. **Web event types**:
    - `GPUUncapturedErrorEvent`, `GPUUncapturedErrorEventInit`  
      These are specialized event types tied to WebGPU error handling in browsers. For contexts where WebGPU error
      events are not managed or required, these types can safely be ignored.

5. **Web worker-related types**:
    - `GPUExternalTexture`, `GPUExternalTextureDescriptor`, `GPUExternalTextureBindingLayout`,
      `GPUCopyExternalImageSource`, `GPUCopyExternalImageDestInfo`, `GPUCopyExternalImageSourceInfo`  
      These types focus on uses in Web Workers or involve advanced external texture handling. Unless the implementation
      specifically aims to handle such cases, these types are excluded to reduce complexity.

By ignoring these types, the implementation remains focused, avoiding unnecessary dependencies on web-specific or
redundant details.

# "Dict" type treatment

Union types that include a `sequence` and a "Dict" type will be transformed into a single dictionary type to ensure
structured, clear, and portable type definitions. For example:

The following type definition:

```webidl
typedef (sequence<double> or GPUColorDict) GPUColor;
```

will be converted into a single dictionary type as follows:

```webidl
dictionary GPUColor {
    required double r;
    required double g;
    required double b;
    required double a;
};
```

This transformation simplifies the type definition by removing the `sequence` and focusing solely on a structured
`GPUColor` dictionary. This approach ensures that the definition is more explicit, reduces ambiguity, and provides
consistency when handling properties such as `r`, `g`, `b`, and `a`. Structured dictionary types like this are preferred
as they communicate intent more clearly and align with implementation goals.

# Enumerations

The enumerations will be inferred based on the values specified in the WebGPU C header specifications,
available [here](https://github.com/webgpu-native/webgpu-headers/blob/main/webgpu.yml). This approach ensures that the
Kotlin representations of WebGPU enumerations remain consistent with the official WebGPU specifications. By leveraging
these predefined values, developers can maintain compatibility with the WebGPU standard while reducing the risk of
discrepancies in the implementation.

Additionally, the naming of these enumerations will be based on the C specifications rather than the IDL values. This
choice is made because the C specification names feel more natural in terms of language usage. 
By following the conventions of the C headers, the implementation adopts a clearer and more intuitive
representation of enumerations, making them easier for developers to work with and aligning better with the nature of
the WebGPU API.

# Constants

Constants and their respective values will be transformed into enumerations to ensure type safety. 
Unlike other types, the default type for these enumerations and their values will be explicitly set to `ULong`. 
This decision is made to align with the data type used for constants in the native WebGPU headers. 
By adhering to this approach, we ensure consistency with the official WebGPU implementation while 
maintaining compatibility across supported platforms.

# Dictionary and interface

Dictionary types and interfaces in WebGPU will be transformed into Kotlin `interface` constructs. This design choice
reflects the flexibility and extensibility offered by Kotlin interfaces, ensuring that implementations are both
platform-agnostic and highly adaptable to various project requirements.

For the initial target, the **wgpu4k** project (or a hypothetical **dawn4k** project), the transformation will primarily
focus on providing basic and essential implementations of these interfaces. The objective here is to offer a
straightforward and functional mapping of WebGPU dictionaries and interfaces, ensuring compatibility with the core
WebGPU API.

However, this is not a restrictive approach. Other projects or extensions can easily build upon these interfaces to
deliver more advanced functionalities, such as offering domain-specific languages (DSLs) or rich utility methods for a
more expressive developer experience. By relying on Kotlin's support for interface inheritance, any implementation can
extend the base interfaces without restricting compatibility. This modular approach guarantees that developers can work
with any compatible interface, whether it uses basic functionality or advanced DSLs, without being tightly coupled to a
specific implementation.

> **Note**: Each specification must implement default values as described in the corresponding IDL definitions. These
> default values ensure consistency with the WebGPU standard and provide clear guidance for initialization behaviors. By
> adhering to the default values in the IDL specifications, the implementation will maintain compliance and avoid
> discrepancies across different platforms.

