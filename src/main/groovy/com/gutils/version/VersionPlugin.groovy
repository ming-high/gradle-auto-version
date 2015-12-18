package com.gutils.version

import com.gutils.version.utils.Constants
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.gutils.version.opts.VersionExtension
import com.gutils.version.opts.VersionCodeType

/**
 * 版本管理插件
 */
class VersionPlugin implements Plugin<Project> {

    void apply(Project project) {

        def mainVersionExt = project.extensions.create(Constants.EXT_MAIN, VersionExtension, project)

        project.afterEvaluate {
            for (dependentTask in mainVersionExt.codeOptions.dependsOnTasks) {
                for (String taskName in project.gradle.startParameter.taskNames) {
                    if (taskName.toLowerCase(Locale.ENGLISH).contains(dependentTask) &&
                            mainVersionExt.codeOptions.versionCodeType == VersionCodeType.AUTO_INCREMENT_ONE_STEP) {
                        def versionPropsFile = mainVersionExt.codeOptions.versionFile
                        if (versionPropsFile.canRead()) {
                            def Properties versionProps = new Properties()
                            versionProps.load(new FileInputStream(versionPropsFile))
                            def code = mainVersionExt.versionCode

                            versionProps[Constants.AI_VERSION_CODE] = code.toString()
                            versionProps.store(versionPropsFile.newWriter(), null)
                        }
                    }
                }
            }

        }
    }

}
