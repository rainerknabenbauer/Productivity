<script>
  import { createEventDispatcher } from "svelte";
  const dispatch = createEventDispatcher();

  export let project;

  let isTitleBeingEdited = false;

  function showReminder() {
    dispatch("showReminder");
  }

  function showFAQ() {
    dispatch("showFAQ");
  }

  function showTrashbin() {
    dispatch("showTrashbin");
  }

  function showHistory() {
    dispatch("showHistory");
  }

  function showFirstSteps() {
    dispatch("showFirstSteps");
  }

  function showPinboard() {
    dispatch("showPinboard");
  }

  function toggleEditTitle() {
    isTitleBeingEdited = !isTitleBeingEdited;
  }

  function toggleProtection() {
    project.isProtected = !project.isProtected;
    saveProject();
  }

  function updateTitle() {
    toggleEditTitle();
    document.title = project.name;
    saveProject();
  }

  function saveProject() {
    dispatch('saveProject', {
			text: JSON.stringify(project)
		});
  }

  function showTaskDetails() {
    dispatch("showTaskDetails");
  }
</script>


<header class="header">

    <div class="edit" on:click={showTaskDetails}>
      <img src="./img/edit.png" alt="Add new task">
    </div>
    

    {#if isTitleBeingEdited}
      <input class="editName" type="text" id="fname" name="fname" bind:value={project.name}/>
      <button on:click={updateTitle}>OK</button>
    {:else}
      <div class="projectName" on:click={toggleEditTitle}>{project.name}</div>
    {/if}
    
    <div class="actionItems tooltip" on:click={showFAQ}>
      <i class="fa fa-angle-down" />
      <span class="tooltiptext">More</span>
    </div>

    <div class="actionItems tooltip" on:click={showTrashbin}>
      <i class="fa fa-trash" />
      <span class="tooltiptext">Undo</span>
    </div>

    <div class="actionItems tooltip" on:click={showFirstSteps}>
      <i class="fa fa-question-circle"/>
      <span class="tooltiptext">Need help?</span>
    </div>

    <div class="actionItems tooltip" on:click={toggleProtection}>
      {#if project.isProtected }
      <i class="fa fa-unlock-alt" />
      <span class="tooltiptext">Protected <i class="fa fa-check" /></span>
      {:else}
      <i class="fa fa-unlock" />
      <span class="tooltiptext">Enable protection</span>
      {/if}
    </div>

    <div class="actionItems tooltip" on:click={showReminder}>
      {#if project.email === null}
      <i class="fa fa-bell-o" />
      <span class="tooltiptext">Set eMail!</span>
      {:else}
      <i class="fa fa-bell" />
      <span class="tooltiptext">eMail set <i class="fa fa-check" /></span>
      {/if}
    </div>

    <div class="actionItems tooltip">
      <i class="fa fa-home" on:click={showPinboard}/>
      <span class="tooltiptext">Home</span>
    </div>
  </header>


<style>
  .edit {
    float: left;
    padding: 1px 48px 0 36px;
    height: 32px;
    opacity: 0.75;
  }
  .edit:hover, .actionItems:hover {
    opacity: 0.5;
    border-radius: 50%;
    text-align: center;
  }

  .actionItems {
    float: right;
    padding: 4px 24px 0 24px;
    color: #585858;
    opacity: 0.8;

    font-size: 16pt;
    text-align: center;
    letter-spacing: 1px;
    text-decoration: none;
  }

  .projectName {
    font-family: 'Tangerine', cursive;
    font-size: 2em;
    color: #636363;
    float: left;
    margin-top: -2px;
  }
  .editName {
    height: 40px;
  }
  .tooltip {
  position: relative;
  display: inline-block;
}

.tooltip .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  font-size: 8pt;
  opacity: 0.5;
  
  /* Position the tooltip */
  position: absolute;
  z-index: 1;
  top: 100%;
  left: 50%;
  margin-left: -60px;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
}
  .header {
    height: 50px;
    padding-top: 5px;
    min-width: 500px;
    border: 1px solid transparent;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.03) 0px 2px 5px 0px;
    background-color: #bdcad9;
    background-image: linear-gradient(315deg, #bdcad9 0%, #e1dada 74%);

  }
</style>
