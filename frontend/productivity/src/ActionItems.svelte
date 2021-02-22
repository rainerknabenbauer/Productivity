<script>
  import { createEventDispatcher } from "svelte";
  const dispatch = createEventDispatcher();

  export let project;

  let isTitleBeingEdited = false;

  function showReminder() {
    dispatch("showReminder");
  }

  function showSetPassword() {
    dispatch("showSetPassword");
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
    if (project.isProtected) {
      project.isProtected = !project.isProtected;
      saveProject();
    } else {
      showSetPassword();
    }
  }

  function logout() {
    dispatch("logout");
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

  function repositionFocus() {
    dispatch('repositionFocus');
  }
</script>

{#if !(project.name == undefined)}
<header class="header">

    <div class="edit" on:click={showTaskDetails}>
      <i class="fa fa-edit image" />
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
      <span class="tooltiptext">Archive</span>
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
      <span class="tooltiptext">Set password</span>
      {/if}
    </div>

    <div class="actionItems tooltip" on:click={showReminder}>
      {#if project.email == ""}
      <i class="fa fa-bell-o" />
      <span class="tooltiptext">Set eMail!</span>
      {:else}
      <i class="fa fa-bell" />
      <span class="tooltiptext">eMail set <i class="fa fa-check" /></span>
      {/if}
    </div>

    <div class="actionItems tooltip">
      <i class="fa fa-code-fork" on:click={repositionFocus}/>
      <span class="tooltiptext">Customize focus</span>
    </div>

    <div class="actionItems tooltip">
      <i class="fa fa-home" on:click={showPinboard}/>
      <span class="tooltiptext">Home</span>
    </div>

    {#if project.isProtected}
    <div class="actionItems tooltip" on:click={logout}>
      <i class="fa fa-sign-out" />
      <span class="tooltiptext">Sign out</span>
    </div>
    {:else}
    <div class="actionItems tooltip" on:click={logout}>
      
    </div>
    {/if}
  </header>
{/if}

<style>
  .image {
    font-size: 32pt;
    color: #eeeeee;
  }
  .edit {
    float: left;
    padding: 1px 48px 0 36px;
  }
  .edit:hover, .actionItems:hover {
    opacity: 0.5;
    border-radius: 50%;
    text-align: center;
  }

  .actionItems {
    float: right;
    padding: 4px 24px 0 24px;
    color: #eeeeee;
    opacity: 0.8;

    font-size: 16pt;
    text-align: center;
    letter-spacing: 1px;
    text-decoration: none;
  }

  .projectName {
    font-family: 'Tangerine', cursive;
    font-size: 2em;
    color: #f1f1f1;
    float: left;
    margin-top: 2px;
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
  background-color: rgb(240, 240, 240);
  color: rgb(44, 44, 44);
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  font-size: 8pt;
  opacity: 0.65;
  
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
}
</style>
