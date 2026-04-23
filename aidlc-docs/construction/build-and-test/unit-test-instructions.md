# Unit Test Execution

## Run Unit Tests

### 1. Execute All Unit Tests
```bash
./gradlew :cards:testDebugUnitTest
```

### 2. Review Test Results
- **Expected**: 17 parser tests pass, 0 failures
- **Test Report Location**: `cards/build/reports/tests/testDebugUnitTest/index.html`
- **XML Results**: `cards/build/test-results/testDebugUnitTest/`

### 3. Test Coverage
```bash
./gradlew :cards:testDebugUnitTest --info
```
- **Target**: Minimum 80% code coverage
- **Note**: Full coverage requires additional renderer tests and PBT tests (to be added)

## Current Test Suite

### Parser Tests (CardSchemaParserTest.kt — 17 tests)
- `parse minimal card with single text element`
- `parse all 9 action types`
- `parse ColorOrHex as plain hex string`
- `parse ColorOrHex as themed object`
- `parse Padding as uniform number`
- `parse Padding as per-side object`
- `parse Dimension as dp number`
- `parse Dimension as percentage string`
- `parse Dimension as auto string`
- `parse ContainerStyle with all fields`
- `unknown fields are silently ignored`
- `malformed JSON returns failure`
- `empty body array parses successfully`
- `round trip serialization produces equivalent model`
- `parse accordion with string header`
- `parse accordion with element array header`
- `parse tabs element`
- `parse nested layout elements`
- `parse table element`

## Tests Still Needed (Future)
- Theme resolver unit tests
- Action emitter validation tests
- Individual renderer unit tests (20 renderers)
- Property-based tests (28 correctness properties via Kotest)
- Snapshot tests (Roborazzi)

### Fix Failing Tests
If tests fail:
1. Review test output in `cards/build/reports/tests/testDebugUnitTest/index.html`
2. Check serialization configuration in `CardSchemaParser.kt`
3. Verify test fixtures in `cards/src/test/resources/fixtures/`
4. Rerun tests until all pass
