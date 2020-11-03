package commands.impl;

import filesystem.Directory;
import filesystem.FileSystem;
import org.junit.Test;

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

        Directory d = fs.getCurrent().getSubDir("test1");
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
}