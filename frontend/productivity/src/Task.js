class Task {
	constructor() {
		this.id = undefined;
		this.isBeingWorkedOn = undefined;
		this.title = "";
		this.description = {
			shortDescription: "",
			longDescription: "",
			additionalNotes: undefined
		};
        this.endDate = undefined;
        this.priority = 0;
        this.preDependency = undefined;
		this.postDependency = undefined;
		this.ui = {
			xposition: 5,
			yposition: 45
		};
	}
}

export default Task;