# Unit of Work — Requirements Story Map

Since User Stories were skipped, this maps requirements directly to units.

## Unit 1: Models & Parser

| Requirement | Coverage |
|-------------|----------|
| FR-2 (Card Schema Parsing) | Full — all 20 element types, 9 action types, polymorphic serialization, round-trip, unknown field handling |
| FR-1.1 (Parse body array) | Partial — parsing portion |
| FR-1.5 (Missing body/version detection) | Partial — parser returns error |

## Unit 2: Theme & Core Infrastructure

| Requirement | Coverage |
|-------------|----------|
| FR-9 (Theme Resolution) | Full — ColorValue resolution, URL resolution, precedence chain, auto/manual mode |
| FR-10 (Default Theme) | Full — all color tokens, typography scale, font family |
| FR-8 (Action Emission) | Full — all 9 action types, validation, no-callback no-op |
| FR-12 (Loading State API) | Full — setElementLoading, isLoading, clearAll |
| FR-13 (Logging) | Full — log levels, filtering, format |

## Unit 3: Content & Data Renderers

| Requirement | Coverage |
|-------------|----------|
| FR-4 (Content Element Rendering) | Full — all 11 content types with all properties |
| FR-7 (Data Display Rendering) | Full — table with headers, rows, striped, borders |
| FR-14 (Accessibility) | Partial — content element labels, altText, decorative images |
| NFR-1 (Performance) | Partial — async image loading with Coil caching |

## Unit 4: Layout & Interactive Renderers

| Requirement | Coverage |
|-------------|----------|
| FR-5 (Layout Element Rendering) | Full — all 5 layout types, recursive rendering |
| FR-6 (Interactive Element Rendering) | Full — all 3 interactive types, 4 button variants |
| FR-15 (Max Nesting Depth) | Full — depth tracking, skip at >5 |
| FR-14 (Accessibility) | Partial — interactive element labels, accordion/tab state announcements |

## Unit 5: Public API & Integration

| Requirement | Coverage |
|-------------|----------|
| FR-1 (Card Rendering Entry Point) | Full — CometChatCardView + CometChatCardComposable |
| FR-3 (Element Registry) | Full — registry initialization with all 20 renderers |
| FR-11 (Error Handling) | Full — fallbackText, placeholder, error chain |
| FR-9.7-9.8 (Auto mode system observation) | Full — Configuration.uiMode + isSystemInDarkTheme() |
| NFR-2 (Module Structure) | Full — Gradle module setup, Maven publish |
| NFR-4 (Dependencies) | Full — all dependency declarations |
| NFR-5 (Compatibility) | Full — minSdk, targetSdk, Kotlin version |

## Coverage Summary

| Requirement | Unit(s) |
|-------------|---------|
| FR-1: Entry Point | Unit 1 (parse), Unit 5 (orchestration) |
| FR-2: Schema Parsing | Unit 1 |
| FR-3: Element Registry | Unit 5 |
| FR-4: Content Rendering | Unit 3 |
| FR-5: Layout Rendering | Unit 4 |
| FR-6: Interactive Rendering | Unit 4 |
| FR-7: Data Display | Unit 3 |
| FR-8: Action Emission | Unit 2 |
| FR-9: Theme Resolution | Unit 2 |
| FR-10: Default Theme | Unit 2 |
| FR-11: Error Handling | Unit 5 |
| FR-12: Loading State | Unit 2 |
| FR-13: Logging | Unit 2 |
| FR-14: Accessibility | Unit 3, Unit 4 |
| FR-15: Nesting Depth | Unit 4 |
| NFR-1: Performance | Unit 3 (image caching) |
| NFR-2: Module Structure | Unit 5 |
| NFR-3: Testing | All units (tests per unit) |
| NFR-4: Dependencies | Unit 5 |
| NFR-5: Compatibility | Unit 5 |
| NFR-6: Cross-Platform Consistency | Unit 1 (naming), Unit 2 (defaults) |
