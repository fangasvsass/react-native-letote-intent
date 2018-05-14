
# react-native-letote-intent

## Getting started

`$ npm install react-native-letote-intent --save`

### Mostly automatic installation

`$ react-native link react-native-letote-intent`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-letote-intent` and add `RNLetoteIntent.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNLetoteIntent.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNLetoteIntentPackage;` to the imports at the top of the file
  - Add `new RNLetoteIntentPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-letote-intent'
  	project(':react-native-letote-intent').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-letote-intent/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-letote-intent')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNLetoteIntent.sln` in `node_modules/react-native-letote-intent/windows/RNLetoteIntent.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Letote.Intent.RNLetoteIntent;` to the usings at the top of the file
  - Add `new RNLetoteIntentPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNLetoteIntent from 'react-native-letote-intent';

RNLetoteIntent.gotoPermissionSetting();
android only
RNLetoteIntent.openActivity(String action);
```
  
