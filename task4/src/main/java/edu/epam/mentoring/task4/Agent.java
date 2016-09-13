package edu.epam.mentoring.task4;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by Yevgeniy_Vtulkin on 9/12/2016.
 */
public class Agent {
    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new LoggerClassFileTransformer(), true);
    }

    private static class LoggerClassFileTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader,
                                String className,
                                Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer)
                throws IllegalClassFormatException {
            if (className.equals("org/apache/log4j/Category")) {
                ClassPool classPool = ClassPool.getDefault();
                try {
                    CtClass ctClass = classPool.getCtClass(className.replaceAll("/", "."));
                    CtMethod method = ctClass.getMethod("debug", "(Ljava/lang/Object;)V");
                    method.insertBefore("message = \"Yevgeniy says: \" + (String)message;");
                    return ctClass.toBytecode();
                } catch (NotFoundException e) {
                    System.out.println("CLASS NOT FOUND");
                } catch (CannotCompileException e) {
                    System.out.println("COMPILE EXCEPTION");
                } catch (IOException e) {
                    System.out.println("IO EXCEPTION");
                }
            }

            return classfileBuffer;
        }
    }
}
