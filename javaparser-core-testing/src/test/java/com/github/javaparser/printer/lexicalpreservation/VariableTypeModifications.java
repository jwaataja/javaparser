/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2019 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.github.javaparser.printer.lexicalpreservation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class VariableTypeModifications {
    @Test
    public void test() {
        CompilationUnit cu = StaticJavaParser.parse(
                "public class Bar {\n" +
                "    String myField;\n" +
                "}"
                );
        FieldDeclaration field = cu.findFirst(FieldDeclaration.class).get();
        System.out.println("Before setup, maximum common type is: " + field.getMaximumCommonType());
        LexicalPreservingPrinter.setup(cu);
        System.out.println("Before, maximum common type is: " + field.getMaximumCommonType());
        // Find the String type and change it to Integer.
        Optional<ClassOrInterfaceType> type = cu.findFirst(ClassOrInterfaceType.class);
        // type.get().setName("Integer");
        System.out.println("Type is now: " + type);
        // Print the result from LexicalPreservingPrinter.
        String result = LexicalPreservingPrinter.print(cu);
        System.out.println("The AST we're testing is: " + cu.toString());
        System.out.println("After, maximum common type is: " + field.getMaximumCommonType());
        // Verify that the name of the type was changed.
        assertTrue(result.contains("Integer"));
    }

    @Test
    public void test2() {
        System.out.println("In test 2");
        CompilationUnit cu = StaticJavaParser.parse(
                "public class Foo {\n" +
                "    String myField;\n" +
                "}"
                );
        LexicalPreservingPrinter.setup(cu);

        FieldDeclaration field = cu.findFirst(FieldDeclaration.class).get();
        System.out.println("Maximum common type is: " + field.getMaximumCommonType());

        // // Insert the annotation onto the String type.
        // Optional<ClassOrInterfaceType> type = cu.findFirst(ClassOrInterfaceType.class);
        // System.out.println("Found type: " + type);
        // type.get().addAnnotation(new MarkerAnnotationExpr("Nullable"));
        // System.out.println("After adding annotation: " + type.get());
        // System.out.println("The compilation unit contains: " + cu);
        // String result = LexicalPreservingPrinter.print(cu);
        // System.out.println(result);
        // // Verify that there's a space between the annotation and the String type.
        // // assertTrue(result.contains("@Nullable String"));

        System.out.println(LexicalPreservingPrinter.print(cu));
    }
}
