# Application Design Plan

## Overview
Design the high-level component architecture for the CometChatCardsRenderer Android library based on the design document and approved requirements.

## Execution Checklist

- [ ] Step 1: Identify main components and their responsibilities
- [ ] Step 2: Define component interfaces (renderer interface, registry, parser, theme resolver)
- [ ] Step 3: Design service layer (CometChatCardView entry point orchestration)
- [ ] Step 4: Establish component dependencies and communication patterns
- [ ] Step 5: Generate design artifacts (components.md, component-methods.md, services.md, component-dependency.md, application-design.md)

## Clarifying Questions

The design document is comprehensive. The following questions address Android-specific architectural decisions not covered in the cross-platform design.

---

## Question 1: View/Compose Code Sharing Strategy
The design says "core rendering logic is shared; framework-specific layers are thin wrappers." How should this be structured in the module?

A) Single `:cards` module with shared core logic + View wrappers + Compose wrappers in separate source sets
B) Single `:cards` module with all code in one source set, using interfaces to abstract View vs Compose rendering
C) Two modules: `:cards-core` (models, parser, theme, registry) + `:cards` (View + Compose renderers that depend on core)
X) Other (please describe after [Answer]: tag below)

[Answer]: C

---

## Question 2: Renderer Implementation Pattern
Each of the 20 element renderers needs to produce both a View and a Composable. How should renderers be structured?

A) Each element has TWO renderer classes: `TextElementViewRenderer` (produces View) and `TextElementComposeRenderer` (produces @Composable) — registry holds both
B) Each element has ONE renderer class with two methods: `renderView()` and `renderComposable()` — single registry
C) Compose-first approach: renderers produce @Composable only, View API wraps Compose via `ComposeView`/`AndroidView`
X) Other (please describe after [Answer]: tag below)

[Answer]: B

---

## Question 3: Coil Integration Approach
Coil supports both View (ImageView extension) and Compose (AsyncImage). How should image loading be integrated?

A) Use Coil's View extensions for View renderers and Coil's Compose extensions for Compose renderers (separate integration per API)
B) Use Coil's ImageLoader directly for both, with custom integration
C) Use Coil Compose only, wrap with AndroidView for traditional View usage
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 4: Theme Override API Design
The Theme_Override allows consumers to override Default_Theme values. How should this be exposed?

A) Data class `CometChatCardThemeOverride` with nullable fields for each overridable token (colors, typography, fontFamily)
B) Builder pattern: `CometChatCardThemeOverride.Builder().textColor(...).buttonFilledBg(...).build()`
C) Map-based: `Map<String, Any>` where keys are token names
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 5: Sealed Class Deserialization Strategy
Kotlinx Serialization requires explicit polymorphic configuration for sealed classes. How should the 20 element types be registered?

A) Use `@Serializable` with `@SerialName` on each element class + `SerializersModule` with `polymorphic` block registering all 20 types
B) Use a custom `JsonContentPolymorphicSerializer` that inspects the `type` field to determine the concrete class
C) Use `@JsonClassDiscriminator("type")` annotation on the sealed interface with `@SerialName` on each subclass
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

Please answer each question by filling in the letter choice after the `[Answer]:` tag. Let me know when you're done.
