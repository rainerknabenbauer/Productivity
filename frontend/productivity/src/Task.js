class Task {
	constructor() {
		this.id = undefined;
		this.projectId = undefined;
		this.isBeingWorkedOn = undefined;
		this.title = "";
		this.description = {
			shortDescription: "",
			longDescription: "",
			additionalNotes: undefined
		};
        this.endDate = undefined;
        this.priority = 0;
        this.followups = undefined;
		this.ui = {
			xposition: 5,
			yposition: 45
		};
	}

	static from(json){
		return Object.assign(new Task(), json);
	  }
}

export default Task;