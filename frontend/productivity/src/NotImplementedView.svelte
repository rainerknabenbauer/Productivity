<script>

</script>

<div class="containers w3-light-grey">
    <div class="sidenav">

                <div class="sidenavelement">
                    Not implemented, yet
                </div>
    </div>
</div>

<style>
    * {
        position: relative;
        box-sizing: border-box;
        z-index: 5;
    }

    /* Style the side navigation */

    .containers {
        position: absolute;
        display: flex;
        flex-direction: row;
        justify-content: center;
        border-style: solid;
        border-width: 1px;
        border-color: lightslategray;
        width: 45%;
        right: 8px;
    }

    .sidenav {
        height: 100%;
        width: 100%;
        position: relative;
        top: 0;
        left: 0;
        overflow-x: hidden;
        padding: 10px 0px 10px 0px;
        margin: 0px;
        border-width: 1px;
        background-color: #dddddd;
    }

    /* Side navigation links */
    .sidenavelement {
        padding: 3px 10px;
        text-decoration: none;
        width: 100%;
        letter-spacing: 0.1px;
        text-decoration: none;
        font-family: "Verdana";
    }

    /* Change color on hover */
    .sidenavelement:hover {
        background-color: #e9e9e9;
        color: black;
    }
</style>
