class Task {
	constructor() {
		this.id = undefined;
		this.isActive = undefined;
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
			xposition: 0,
			yposition: 0
		};
	}
}

export default Task;