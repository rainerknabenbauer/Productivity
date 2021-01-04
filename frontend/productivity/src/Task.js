class Task {
    constructor(id, title, description, endDate, priority, preDependency, postDependency) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.priority = priority;
        this.preDependency = preDependency;
        this.postDependency = postDependency;
    }

}

export default Task;


/*
In App.svelte

<script>
	import MoveableBlock from './MoveableBlock.svelte';
	import Task from './Task.js';

	async function getTasks() {
		const res = await fetch('http://localhost:8080/tasks');
        return await res.json();
		let json =  await res.json();
		
		if (res.ok) {
			return Object.assign(new Task(), json);;
		} else {
			const failed = new Task();
			failed.applyData(({ title: "server error" }));
			return failed;
		}
    }

*/