# Requirements Verification Questions

The design document and requirements document are comprehensive. The following questions address remaining ambiguities and extension opt-in decisions.

Please answer each question by filling in the letter choice after the `[Answer]:` tag.

---

## Question 1: Scope Confirmation — Android Only
The design document covers all platforms (Android, iOS, Flutter, Web, React Native). For this Linear ticket (ENG-34127), are we building the Android platform package only?

A) Yes — Android only (Traditional View + Jetpack Compose APIs)
B) No — include additional platforms (please specify after [Answer]: tag)
X) Other (please describe after [Answer]: tag below)

[Answer]: Yes the android platform only.

---

## Question 2: Jetpack Compose Support
The design specifies both Traditional View API and Jetpack Compose API. Should both be implemented in this iteration?

A) Yes — implement both View and Compose APIs
B) View API only first, Compose later
C) Compose API only first, View later
X) Other (please describe after [Answer]: tag below)

[Answer]: The library needs to work with both traditional views and compose. 

---

## Question 3: Image Loading Library
The design specifies Coil for Android image loading. Confirm the image loading library choice:

A) Coil (as specified in design doc)
B) Glide
C) Picasso
X) Other (please describe after [Answer]: tag below)

[Answer]: Yes A.

---

## Question 4: Markdown Rendering Approach
The design specifies using `Html.fromHtml` for Android View and `AnnotatedString` for Compose. Should we use a dedicated markdown library instead?

A) Use Html.fromHtml / AnnotatedString as specified (built-in approach)
B) Use a markdown library (e.g., Markwon for View, richtext-commonmark for Compose)
C) Use Markwon for both View and Compose (Markwon has Compose support)
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 5: JSON Parsing Library
The design mentions type-discriminated deserialization. Which JSON library should be used?

A) Kotlinx Serialization (with sealed class polymorphic serialization)
B) Gson (with RuntimeTypeAdapterFactory)
C) Moshi (with sealed class adapter)
X) Other (please describe after [Answer]: tag below)

[Answer]:  A

---

## Question 6: Library Module Structure
The current project has a single `app` module. The card renderer should be a separate library module. Confirm the module structure:

A) Create a new `cards` library module (`:cards`) alongside the existing `:app` module, with `:app` as a demo/test app
B) Convert the existing `:app` module into the library module
C) Create the library as a standalone project separate from this repo
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 7: Package Name
The design specifies `com.cometchat.cards` as the package name. The current project uses `org.cometchat.cardrender`. Confirm:

A) Use `com.cometchat.cards` as specified in the design document
B) Use `org.cometchat.cardrender` (match existing project)
C) Use `com.cometchat.cardrender`
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 8: Default Theme Color Values
The design document lists all Default_Theme color tokens as "TBD" (to be finalized by design team). How should we handle this?

A) Use the placeholder values from the design doc's Default_Theme Structure section (e.g., textColor light=#141414, dark=#E8E8E8, etc.) — these are reasonable defaults
B) Leave all theme colors as configurable-only with no hardcoded defaults (require Theme_Override for all colors)
C) I will provide the final color values before code generation
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 9: Shimmer Implementation
The design mentions shimmer for image loading states. Which shimmer library should be used?

A) Facebook Shimmer library (`com.facebook.shimmer`)
B) Custom shimmer implementation using Compose animation / Canvas drawing
C) Coil's built-in placeholder with a simple color placeholder (no shimmer animation)
X) Other (please describe after [Answer]: tag below)

[Answer]: B

---

## Question 10: Snapshot Testing Library
The design mentions Paparazzi or Roborazzi for Compose snapshot tests. Which one?

A) Paparazzi (Cashapp, no device/emulator needed)
B) Roborazzi (Robolectric-based, no device needed)
C) Skip snapshot tests for now, focus on unit + property-based tests
X) Other (please describe after [Answer]: tag below)

[Answer]: B

---

## Question 11: Minimum Viable Scope
The design includes "GOOD TO HAVE" features (loading state API for interactive elements). Should these be included in the first iteration?

A) Yes — include loading state API (setElementLoading / clearElementLoading)
B) No — skip good-to-have features, focus on core rendering only
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 12: Security Extensions
Should security extension rules be enforced for this project?

A) Yes — enforce all SECURITY rules as blocking constraints (recommended for production-grade applications)
B) No — skip all SECURITY rules (suitable for PoCs, prototypes, and experimental projects)
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 13: Property-Based Testing Extension
Should property-based testing (PBT) rules be enforced for this project?

A) Yes — enforce all PBT rules as blocking constraints (recommended for projects with business logic, data transformations, serialization, or stateful components)
B) Partial — enforce PBT rules only for pure functions and serialization round-trips (suitable for projects with limited algorithmic complexity)
C) No — skip all PBT rules (suitable for simple CRUD applications, UI-only projects, or thin integration layers with no significant business logic)
X) Other (please describe after [Answer]: tag below)

[Answer]: 
A
---

## Question 14: Distribution Format
The design mentions Maven Central / GitHub Packages for Android distribution. Which distribution channel?

A) Maven Central (public, standard for open-source)
B) GitHub Packages (private, org-scoped)
C) Both Maven Central and GitHub Packages
D) Local AAR only for now, distribution setup later
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

## Question 15: Kotlin Version and Language Features
Any constraints on Kotlin version or language features?

A) Use latest stable Kotlin (currently 2.1.x) with all modern features (sealed interfaces, value classes, context receivers)
B) Use Kotlin 1.9.x for broader compatibility
C) Match whatever Kotlin version the current Gradle setup uses
X) Other (please describe after [Answer]: tag below)

[Answer]: A

---

Once you've answered all questions, let me know and I'll proceed with generating the formal requirements document.
