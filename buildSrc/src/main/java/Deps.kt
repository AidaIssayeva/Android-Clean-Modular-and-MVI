object Version {
    const val dagger = "2.23.2"
    const val mosby = "2.0.1"
    const val glide = "4.9.0"
    const val glide_transformations = "4.1.0"
    const val epoxy = "3.11.0"
    const val rx_binding = "3.0.0"
    const val kotlin = "1.4.0"
    const val lifecycle = "2.2.0"
    const val retrofit = "2.6.1"
    const val koptional = "1.6.0"
    const val conductor = "3.0.0-rc1"
    const val room = "2.2.5"
    const val lint = "26.5.0" //gradle plugin version+23
    const val three_ten = "1.2.1"
    const val view_pager_2 = "1.0.0"
    const val okhttp = "4.2.1"
    const val paging = "2.1.2"
    const val moshi = "1.8.0"
}

object BuildVersion {
    const val minSdkVersion = 26
    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
    
    const val versionName = "1.0.0"
    const val versionCode = 1
}

object Dependencies {

    const val branch = "io.branch.sdk.android:library:5.0.0"
    const val segment = "com.segment.analytics.android:analytics:4.9.0"
    const val appsflyer = "com.appsflyer:segment-android-integration:1.19"
    const val firebase_segment = "com.segment.analytics.android.integrations:firebase:2.1.0@aar"

    const val firebase_config = "com.google.firebase:firebase-config:19.1.3"
    const val firebase_analytics = "com.google.firebase:firebase-analytics:17.2.1"
    const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics:17.3.0"
    const val firebase_appindexing =  "com.google.firebase:firebase-appindexing:19.1.0"
    const val firebase_messaging =  "com.google.firebase:firebase-messaging:20.2.0"

    const val android_arch_runtime = "androidx.arch.core:runtime:2.1.0"
    const val android_arch_common = "androidx.arch.core:common:2.1.0"
    const val core_testing = "androidx.arch.core:core-testing:2.1.0"

    const val mockk = "io.mockk:mockk:1.10.2"

    const val installreferrer = "com.android.installreferrer:installreferrer:1.0"

    // required if your app is in the Google Play Store
    const val play_ads =  "com.google.android.gms:play-services-ads:18.3.0" // GAID matching

    const val play_services =  "com.google.android.gms:play-services-base:17.1.0" //dont use the same versioning for firebase and or other play
    const val play_phone_auth =  "com.google.android.gms:play-services-auth-api-phone:17.4.0"
    const val play_gcm = "com.google.android.gms:play-services-gcm:17.0.0"

    const val kotlin_jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"

    const val tink = "com.google.crypto.tink:tink-android:1.3.0-rc1"

    const val apache_io =  "commons-io:commons-io:2.6"
    const val apache_codec = "commons-codec:commons-codec:1.11"

    const val rx_core = "io.reactivex.rxjava2:rxjava:2.2.10"
    const val rx_binding_core = "com.jakewharton.rxbinding3:rxbinding:${Version.rx_binding}"
    const val rx_relay = "com.jakewharton.rxrelay2:rxrelay:2.1.0"
    const val rx_android = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rx_kotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"

    const val arch_viewmodel = "android.arch.lifecycle:viewmodel:${Version.lifecycle}"
    const val arch_compiler = "android.arch.lifecycle:compiler:${Version.lifecycle}"

    const val koptional_core = "com.gojuno.koptional:koptional:${Version.koptional}"
    const val koptional_rx = "com.gojuno.koptional:koptional-rxjava2-extensions:${Version.koptional}"

    const val dagger_core = "com.google.dagger:dagger:${Version.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Version.dagger}"

    //ui
    const val epoxy_core = "com.airbnb.android:epoxy:${Version.epoxy}"
    const val epoxy_processor = "com.airbnb.android:epoxy-processor:${Version.epoxy}"
    const val epoxy_paging = "com.airbnb.android:epoxy-paging:${Version.epoxy}"
    const val view_pager_2 = "androidx.viewpager2:viewpager2:${Version.view_pager_2}"

    const val androidx_appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val androidx_cardview = "androidx.cardview:cardview:1.0.0"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:1.0.0"
    const val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val androidx_viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val androidx_legacy = "androidx.legacy:legacy-support-v4:1.0.0"
    const val androidx_ktx =  "androidx.core:core-ktx:1.1.0-beta01"
    const val androidx_security = "androidx.security:security-crypto:1.0.0-alpha02"
    const val androidx_biometric =  "androidx.biometric:biometric:1.0.1"
    const val androidx_sqlite =  "androidx.sqlite:sqlite:2.0.1"
    const val androidx_swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
    const val androidx_paging = "androidx.paging:paging-rxjava2-ktx:${Version.paging}"

    const val circle_image_view =   "de.hdodenhof:circleimageview:3.0.1"

    const val glide_core = "com.github.bumptech.glide:glide:${Version.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
    const val glide_annotations = "com.github.bumptech.glide:annotations:${Version.glide}"
    const val glide_integration = "com.github.bumptech.glide:okhttp3-integration:${Version.glide}"
    const val glide_transformations = "jp.wasabeef:glide-transformations:${Version.glide_transformations}"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val material_design = "com.google.android.material:material:1.1.0"
    const val support_design = "com.android.support:design:28.0.0"

    const val lottie = "com.airbnb.android:lottie:3.0.7"
    const val material_dialog_library = "com.afollestad.material-dialogs:core:3.1.1"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
    const val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"

    const val moshi = "com.squareup.moshi:moshi:${Version.moshi}"
    const val moshi_adapters = "com.squareup.moshi:moshi-adapters:${Version.moshi}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"

    const val conductor = "com.bluelinelabs:conductor:${Version.conductor}"
    const val conductor_rx = "com.bluelinelabs:conductor-rxlifecycle2:${Version.conductor}"
    const val conductor_support = "com.bluelinelabs:conductor-support:${Version.conductor}"
    const val conductor_archlifecycle = "com.bluelinelabs:conductor-archlifecycle:${Version.conductor}"

    const val jetbrains_annotations = "org.jetbrains:annotations:16.0.1"

    const val apollo_core = "com.apollographql.apollo:apollo-runtime:2.4.1"
    const val apollo_rx2 = "com.apollographql.apollo:apollo-rx2-support:2.4.1"

    const val simplestore = "com.uber.simplestore:simplestore:0.0.6"

    const val room_runtime = "androidx.room:room-runtime:${Version.room}"
    const val room_compiler = "androidx.room:room-compiler:${Version.room}"
    const val room_ktx = "androidx.room:room-ktx:${Version.room}"
    const val room_rx = "androidx.room:room-rxjava2:${Version.room}"
//    const val room_encryption = "com.commonsware.cwac:saferoom.x:1.2.0"
    const val room_test = "androidx.room:room-testing:${Version.room}"

    const val sqlcipher = "net.zetetic:android-database-sqlcipher:4.3.0@aar"

    const val junit = "junit:junit:4.12"
    const val lint_api = "com.android.tools.lint:lint-api:${Version.lint}"
    const val lint_checks = "com.android.tools.lint:lint-checks:${Version.lint}"
    const val lint_tests = "com.android.tools.lint:lint-tests:${Version.lint}"
    const val assertj_core = "org.assertj:assertj-core:3.9.0"

    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.0-beta-2"

    const val process_phoenix = "com.jakewharton:process-phoenix:2.0.0"
    const val three_ten_abp = "com.jakewharton.threetenabp:threetenabp:${Version.three_ten}"
    const val test_three_ten_bp = "org.threeten:threetenbp:${Version.three_ten}"

    const val androidx_test_rules = "androidx.test:rules:1.1.0"
    const val androidx_test_runner = "androidx.test:runner:1.2.0"
    const val androidx_test_junit = "androidx.test.ext:junit:1.1.1"

    const val test_espresso_core = "androidx.test.espresso:espresso-core:3.2.0"
    const val test_mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"

    const val plaid_linking = "com.plaid.link:sdk-core:2.2.0"

    const val phone_number = "com.googlecode.libphonenumber:libphonenumber:8.2.0"
    const val password_strength_meter_zxcvbn = "com.nulab-inc:zxcvbn:1.2.2"
}

object GradleTemplates {
    const val common_data_source = "common_data_source.gradle"
    const val common_feature = "common_feature.gradle"
    const val common_domain = "common_domain.gradle"
    const val common_view = "common_view.gradle"
}

