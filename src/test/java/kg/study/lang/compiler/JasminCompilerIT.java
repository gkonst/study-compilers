package kg.study.lang.compiler;

import jasmin.Main;
import kg.study.lang.Node;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class JasminCompilerIT {

    public static final File TEST_DIR = new File("target/test-jasmin-compiler");
    private static final String CLASS_NAME = "HelloWorld";
    private static final String CLASS_FILE_NAME = CLASS_NAME + ".j";

    @BeforeMethod
    public void setUp() throws Exception {
        TEST_DIR.mkdirs();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(TEST_DIR);
    }

    @Test
    public void compileShouldNotFailIfEmptyNodeGiven() throws Exception {
        // given
        JasminCompiler compiler = new JasminCompiler();
        Node node = new Node(Node.NodeType.PROGRAM);
        compiler.compile(node);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{TEST_DIR.toURI().toURL()});
        File classFile = new File(TEST_DIR, CLASS_FILE_NAME);
        FileUtils.writeStringToFile(classFile, compiler.getProgram());
        // when
        assemble(classFile.getAbsolutePath(), TEST_DIR.getPath());
        Class clazz = classLoader.loadClass(CLASS_NAME);
        MethodUtils.invokeStaticMethod(clazz, "main", new String[][]{new String[]{}});
        // then
        assertThat(clazz, is(notNullValue()));
    }

    @Test
    public void compileShouldNotFailIfSetAndPrintGive() throws Exception {
        // given
        JasminCompiler compiler = new JasminCompiler();
        // when
        compiler.compile(JasminCompilerTest.SET_AND_PRINT_NODE);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{TEST_DIR.toURI().toURL()});
        File classFile = new File(TEST_DIR, CLASS_FILE_NAME);
        System.out.println(compiler.getProgram());
        FileUtils.writeStringToFile(classFile, compiler.getProgram());
        // when
        assemble(classFile.getAbsolutePath(), TEST_DIR.getPath());
        Class clazz = classLoader.loadClass(CLASS_NAME);
        MethodUtils.invokeStaticMethod(clazz, "main", new String[][]{new String[]{}});
        // then
        assertThat(clazz, is(notNullValue()));
    }

    private static void assemble(String filePath, String destPath) throws Exception {
        Main main = new Main();
        FieldUtils.writeField(main, "dest_path", destPath, true);
        main.assemble(filePath);
    }
}
