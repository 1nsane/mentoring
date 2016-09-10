package edu.epam.mentoring.task4agent;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by Yevgeniy_Vtulkin on 8/27/2016.
 */
public class Agent {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello from Agent");
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
            if (className.equals("org/apache/log4j/Logger")) {
                if (classBeingRedefined == null) {
                    System.out.println("First time loading class");
                }
                System.out.println("className > " + className);
                System.out.println("loader > " + loader);
                ClassReader reader = new ClassReader(classfileBuffer);
                ClassWriter writer = new ClassWriter(reader, 0);
//                ClassPrinter visitor = new ClassPrinter(writer);
//                reader.accept(visitor, 0);
                return writer.toByteArray();
            }
            return classfileBuffer;
        }
    }
}
