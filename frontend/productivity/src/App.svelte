<script>
	import { onMount } from "svelte";
	import Button from "./Button.svelte";
	import MainContent from "./MainContent.svelte";
	import PasswordDialogue from "./PasswordDialogue.svelte";
import ProjectNotFound from "./ProjectNotFound.svelte";

	let tasksPromise = [];
	let isProjectNotFound = false;
	let isLocked = true;

	const host = window.location.hostname;
	const backendUri = production() ? "http://188.34.198.168:8080" : "http://" + host + ":8080";
	const self = production() ? "http://" + host : "http://" + host + ":5000";

	let projectPromise = [];
	let projectId;

	onMount(async () => {
		getUrlParams();
		tasksPromise = getTasks();
		projectPromise = getProject();
	});

	function production() {
		return host == "makemedoit.de";
	}

	function getUrlParams() {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
        
        if (urlParams.has('id')) {
            projectId = urlParams.get('id');
        }
	}

	async function getProject() {
		let result = [];
		if (projectId === undefined || projectId === "") {
			createProject();
		} else {
			result = await fetch(backendUri + "/projects/" + projectId)
				.then((response) => (result = response.json()))
				.catch(() => {
					isProjectNotFound = true;
				});
		}
		return result;
	}

	async function createProject() {
		let newProject = await fetch(backendUri + "/projects/new")
				.then((response) => (response.json()))
				.catch((error) => alert(error));
			reloadPage(newProject.projectId);
	}

	async function getTasks() {
		let result = [];
		if (!(projectId === undefined || projectId === "")) {
			result = await fetch(backendUri + "/tasks/" + projectId)
				.then((response) => response.json())
				.catch((error) => alert(error));
		}
		return result;
	}

	function saveProject(project) {
		fetch("http://" + host + ":8080/projects/", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: project
        });
	}

	function reloadPage(projectReference) {
		location.assign(self + "/?id=" + projectReference);
	}

</script>

{#if isProjectNotFound}
	<ProjectNotFound on:createProject={createProject} />
{:else}
	{#await projectPromise then project}
	{#if project.isProtected && isLocked}
		<PasswordDialogue {project} on:unlock={() => isLocked = false}/>
	{:else}
		{#await tasksPromise then tasks}
		<MainContent {project} {tasks} 
			on:saveProject={(event) => saveProject(event.detail.text)}
			on:undoDelete={(event) => reloadPage(event.detail.text)}
			on:saveTask={() => tasksPromise = getTasks()}
		/>
		{/await}
	{/if}
	{/await}
{/if}

<style>
</style>
