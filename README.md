# android-basic-utils
Android Basic Utils

<h3>Library containing utility classes for most common android tasks.</h3>

[![](https://jitpack.io/v/dasBikash84/android-basic-utils.svg)](https://jitpack.io/#dasBikash84/android-basic-utils)

## Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then, add the library to your module `build.gradle`
```gradle
dependencies {
    implementation 'com.github.dasBikash84:android-basic-utils:latest.release.here'
}
```

## Features
- [`Network`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/NetConnectivityUtility.kt) status and type aquisition related tasks handling made very easy.
- Very simple interface for [`Shared preference`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/SharedPreferenceUtils.kt) related operations. 
- Utility class for Alert [`Dialog`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/DialogUtils.kt) related operations. 
- Utility class for [`toast`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/DisplayUtils.kt) and other display related operations.
- Configurable [`logger`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/LoggerUtils.kt) utility class.
- View and view-controller related many utility [`extension`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/ExtensionFuns.kt) functions.

- [`File`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/FileUtils.kt) and [`Date`](https://github.com/dasBikash84/android-basic-utils/blob/master/android_basic_utils/src/main/java/com/dasbikash/android_basic_utils/utils/DateUtils.kt) operation related utility functions.

License
--------

    Copyright 2020 Bikash Das(das.bikash.dev@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
