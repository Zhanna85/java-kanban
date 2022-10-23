import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Epic extends Task{
    protected ArrayList<Integer> listIdSubtasks; //список уин подзадач

    @Override
    public String toString() {
        return "Epic{" +
                "nameEpic='" + super.getName() + '\'' +
                ", EpicDescription='" + super.getDescription() + '\'' +
                ", uinEpic=" + super.getUin() +
                ", status=" + super.getStatus() +
                ", listIdSubtasks=" + Arrays.asList(listIdSubtasks) +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(listIdSubtasks, epic.listIdSubtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), listIdSubtasks);
    }
}
