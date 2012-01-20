package kg.study.lang.compiler;

import jasmin.Main;
import org.testng.annotations.Test;

public class JasminCompilerIT {
    @Test
    public void test() throws Exception {
        Main main = new Main();
        String filename = "/home/kostya/projects/java/study-compilers/src/test/resources/HelloWorld.j";
        main.assemble(filename);
    }
}
