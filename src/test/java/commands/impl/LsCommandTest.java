package commands.impl;

import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Jorge Báez
 */
public class LsCommandTest {

    @Test
    public void test_ok_ls_command() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));
        fs.getCurrent().create(new Directory("test2",fs.getCurrent()));


        LsCommand command = new LsCommand(null);
        String result = command.execute(fs);

        assertThat(result).isEqualTo("test1\ntest2\n");
        assertThat(fs.getCurrent().getName()).isEqualTo("root");
    }

    @Test
    public void test_ok_ls_full_path_command() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));

        Directory d = fs.getCurrent().getChildDirectory(Arrays.asList("test1"));
        d.create(new Directory("test2",fs.getCurrent()));


        LsCommand command = new LsCommand("test1");
        String result = command.execute(fs);

        assertThat(result).isEqualTo("test2\n");
        assertThat(fs.getCurrent().getName()).isEqualTo("root");
    }

    @Test
    public void test_not_found_ls_command() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));
        fs.getCurrent().create(new Directory("test2",fs.getCurrent()));


        LsCommand command = new LsCommand("test4");
        assertThatThrownBy(() -> command.execute(fs))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Directory not found");
    }

    @Test
    public void test_ls_r_command_ok() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));

        Directory d = fs.getCurrent().getChildDirectory(Arrays.asList("test1"));
        d.create(new Directory("test2",fs.getCurrent()));
        d.create(new File("a.txt",fs.getCurrent(), "test"));


        LsCommand command = new LsCommand("-r");
        String result = command.execute(fs);


        /*
        /root
        test1
        /root/test1
        test2
        a.txt
        */
        assertThat(result).isEqualTo("/root\ntest1\n/root/test1\ntest2\na.txt\n");
        assertThat(fs.getCurrent().getName()).isEqualTo("root");
    }
}