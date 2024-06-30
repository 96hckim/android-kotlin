package com.hocheol.compiler

import com.google.auto.service.AutoService
import com.hocheol.annotations.InstallBinding
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.util.ElementFilter
import javax.lang.model.util.Elements

//@Generated("com.hocheol.compiler.InstallBindingProcessor")
//@Module
//@InstallIn(SingletonComponent.class)
//@OriginatingElement(
//    topLevelClass = AuthenticatorImpl.class
//)
//public interface AuthenticatorImpl_HiltModule {
//  @Binds
//  Authenticator bind(AuthenticatorImpl impl);
//}

@AutoService(Processor::class)
class InstallBindingProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            InstallBinding::class.java.canonicalName
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (annotations.isNullOrEmpty() || roundEnv == null) return false

        annotations.firstOrNull()?.let { processingAnnotation ->
            roundEnv.getElementsAnnotatedWith(processingAnnotation)
                .filterIsInstance<TypeElement>()
                .forEach { processElement(it) }
        }

        return false
    }

    // Annotation 처리
    private fun processElement(typeElement: TypeElement) {
        // Annotation 정보
        val annotation = typeElement.annotationMirrors.first { mirror ->
            val annotationElement = mirror.annotationType.asElement() as TypeElement
            return@first annotationElement.qualifiedName.toString() == InstallBinding::class.java.canonicalName
        }

        // 코드 생성에 필요한 정보
        val annotationTypeElement = annotation.annotationType.asElement() as TypeElement
        val annotationProperties = ElementFilter.methodsIn(annotationTypeElement.enclosedElements).associateBy { it.simpleName.toString() }
        val component = (annotation.elementValues[annotationProperties["component"]]?.value ?: error("component 값을 찾을 수 없습니다.")) as DeclaredType
        val boundType = typeElement.interfaces.first()

        val bindingClassName = ClassName.get(typeElement) // ex) AuthenticatorImpl
        val boundTypeName = TypeName.get(boundType) // ex) Authenticator
        val moduleClassName = ClassName.get(
            bindingClassName.packageName(),
            "${bindingClassName.simpleName()}_HiltModule"
        )

        // 스펙 정의
        val hiltModuleSpec = TypeSpec.interfaceBuilder(moduleClassName)
            .addOriginatingElement(typeElement)
            .addGeneratedAnnotation(processingEnv.elementUtils, processingEnv.sourceVersion)
            .addAnnotation(ClassName.get("dagger", "Module"))
            .addAnnotation(
                AnnotationSpec.builder(ClassName.get("dagger.hilt", "InstallIn"))
                    .addMember("value", "\$T.class", TypeName.get(component))
                    .build()
            )
            .addAnnotation(
                AnnotationSpec.builder(ClassName.get("dagger.hilt.codegen", "OriginatingElement"))
                    .addMember("topLevelClass", "\$T.class", bindingClassName.topLevelClassName())
                    .build()
            )
            .addModifiers(Modifier.PUBLIC)
            .addMethod(
                MethodSpec.methodBuilder("bind")
                    .addAnnotation(ClassName.get("dagger", "Binds"))
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .returns(boundTypeName)
                    .addParameter(bindingClassName, "impl")
                    .build()
            )
            .build()

        // 코드 생성 with Javapoet
        JavaFile.builder(bindingClassName.packageName(), hiltModuleSpec)
            .build()
            .writeTo(processingEnv.filer)
    }

    private fun TypeSpec.Builder.addGeneratedAnnotation(
        elements: Elements,
        sourceVersion: SourceVersion
    ) = apply {
        generatedAnnotationSpec(
            elements,
            sourceVersion,
            InstallBindingProcessor::class.java
        )?.let { generatedAnnotation ->
            addAnnotation(generatedAnnotation)
        }
    }

    private fun generatedAnnotationSpec(
        elements: Elements,
        sourceVersion: SourceVersion,
        processorClass: Class<*>
    ) = elements.getTypeElement(
        if (sourceVersion > SourceVersion.RELEASE_8) {
            "javax.annotation.processing.Generated"
        } else {
            "javax.annotation.Generated"
        }
    )?.let {
        AnnotationSpec.builder(ClassName.get(it))
            .addMember("value", "\$S", processorClass.canonicalName)
            .build()
    }
}