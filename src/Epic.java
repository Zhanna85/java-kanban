import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task{
    protected ArrayList<Integer> listIdSubtasks; //список уин подзадач

    @Override
    public String toString() { // нужен для информативного результата
        return "Epic{" +
                "nameEpic='" + super.getName() + '\'' +
                ", EpicDescription='" + super.getDescription() + '\'' +
                ", uinEpic=" + super.getUin() +
                ", status=" + super.getStatus() +
                ", listIdSubtasks=" + Arrays.asList(listIdSubtasks) +
                '}';

    }

}
