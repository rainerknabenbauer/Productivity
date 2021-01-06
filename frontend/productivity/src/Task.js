class Task {
	constructor() {
		this.id = undefined;
		this.title = "";
		this.description = {
			shortDescription: "",
			longDescription: "",
			additionalNotes: ""
		};
        this.endDate = undefined;
        this.priority = 0;
        this.preDependency = undefined;
		this.postDependency = undefined;
		this.ui = {
			xPosition: 0,
			yPosition: 0
		};
	}
}

export default Task;