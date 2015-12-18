package com.gutils.version.opts

import org.gradle.api.Project

import javax.inject.Inject;

/**
 * 主配置项
 */
class VersionExtension {

    final Project project;
    VersionNameOptions nameOptions
    VersionCodeOptions codeOptions

    @Inject
    VersionExtension(Project project) {
        this.project = project
        nameOptions = new VersionNameOptions()
        codeOptions = new VersionCodeOptions(project)
    }

    void nameOptions(Closure c) {
        project.configure(nameOptions, c)
    }

    void codeOptions(Closure c) {
        project.configure(codeOptions, c)
    }

    void outputOptions(Closure c) {
        project.configure(outputOptions, c)
    }

    int getVersionCode() {
        return codeOptions.versionCode
    }

    String getVersionCodeFormate(int tm){
        int code = getVersionCode()
        Float bigNum = code / tm
        return (bigNum.toInteger()  + "." + code % tm)
    }

    String getVersionCodeFormate(){
        return getVersionCodeFormate(20)
    }

    String getVersionName() {
        return nameOptions.versionName
    }

}
