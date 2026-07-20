// swift-tools-version:5.3
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "CometChatSDK",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(name: "CometChatSDK", targets: ["CometChatSDK","CometChatStarscream"])
    ],
    targets: [
        .binaryTarget(
            name: "CometChatSDK",
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/4.1.6-citest.3/CometChatSDK_4_1_6-citest_3.xcframework.zip",
            checksum: "8cd003a9fbc2078a646a8f4543081d9ee5507375c2c2e60d12ee257f0355773a"
        ),
        .binaryTarget(
            name: "CometChatStarscream",
            url: "https://library.cometchat.io/ios/v4.0/xcode15/CometChatStarscream_1_0_2.xcframework.zip",
            checksum: "94fef289d5b952bb06465e7631be83b40594072931a580a9e8ba86258b9f0ec5"
        )
    ]
)
