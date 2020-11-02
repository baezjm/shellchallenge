package commands.impl;

import filesystem.Directory;
import filesystem.FileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Jorge Báez
 */
@RunWith(MockitoJUnitRunner.class)
public class CdCommandTest {

    @Test
    public void test_ok_command() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));
        fs.getCurrent().create(new Directory("test2",fs.getCurrent()));


        CdCommand command = new CdCommand("test1");
        String result = command.execute(fs);

        assertThat(result).isBlank();
        assertThat(fs.getCurrent().getName()).isEqualTo("test1");
    }

    @Test
    public void test_directory_not_found() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));
        fs.getCurrent().create(new Directory("test2",fs.getCurrent()));


        CdCommand command = new CdCommand("test11");

        assertThatThrownBy(() -> command.execute(fs))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Directory not found");

        assertThat(fs.getCurrent().getName()).isEqualTo("root");
    }

    @Test
    public void test_ok_command_full_path() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));

        Directory d = (Directory) fs.getCurrent().getSubDir("test1");
        d.create(new Directory("test2",fs.getCurrent()));


        CdCommand command = new CdCommand("test1/test2");
        String result = command.execute(fs);


        assertThat(result).isBlank();
        assertThat(fs.getCurrent().getName()).isEqualTo("test2");
    }

    @Test
    public void test_ok_command_up_when_current_is_root() {
        FileSystem fs = new FileSystem(new Directory("root", null));
        fs.getCurrent().create(new Directory("test1",fs.getCurrent()));

        Directory d = (Directory) fs.getCurrent().getSubDir("test1");
        d.create(new Directory("test2",fs.getCurrent()));


        CdCommand command = new CdCommand("..");
        String result = command.execute(fs);


        assertThat(result).isBlank();
        assertThat(fs.getCurrent().getName()).isEqualTo("root");
    }
}