public class Subtask extends Task{

    private int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "nameSubtask='" + super.getName() + '\'' +
                ", SubtaskDescription='" + super.getDescription() + '\'' +
                ", uinSubtask=" + super.getUin() +
                ", status=" + super.getStatus() +
                ", epicId=" + epicId +
                '}';

    }

}
