<!DOCTYPE html>
<head>
  <link rel='icon' href='../img/favicon.ico' type='image/x-icon'/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
    window.gameData = {
    "gameID" : ${gameID},
    "currentUser" : "${currentUser}",
    "viewMode" : "${viewMode}",
    "modeOptions" : ${modeOptionsAsJSON!'{}'},
    "redPlayer" : "${redPlayer}",
    "whitePlayer" : "${whitePlayer}",
    "activeColor" : "${activeColor}"
    };
  </script>
</head>
<body>
  <div class="page">
    <h1>Web Checkers | Game View</h1>

    <#include "nav-bar.ftl" />

    <div class="body">

      <div id="help_text" class="INFO"></div>

      <div>
        <div id="game-controls">

          <fieldset id="game-info">
            <legend>Info</legend>

            <#include "message.ftl" />

            <div>
              <div id="turns">
              <table data-color='RED'>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td class="name">${redPlayer}</td>
                </tr>
              </table>
              <table data-color='WHITE'>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td class="name">${whitePlayer}</td>
                </tr>
              </table>
              </div>
            </div>
          </fieldset>

          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div class="toolbar"></div>
          </fieldset>

        </div>

        <div class="game-board">
          <table id="game-board">
            <tbody>
            <#list board.iterator() as row>
              <tr data-row="${row.index}">
                <#list row.iterator() as space>
                <td data-cell="${space.cellIdx}"
                        <#if space.isValid()>
                          class="Space"
                        </#if>
                >
                  <#if space.piece??>
                    <div class="Piece"
                         id="piece-${row.index}-${space.cellIdx}"
                         data-type="${space.piece.type}"
                         data-color="${space.piece.color}">
                    </div>
                  </#if>
              </#list>
              </tr>
            </#list>
            </tbody>

          </table>
        </div>
      </div>

    </div>
  </div>
  <div id="chat-log">
    <h2>Chat Log</h2>
    <div id="chat-history">
      <p><b>${messages}</b></p>
    </div>
    <form id="postMsg" action="/chat" method="post">
      <div>
	<input type="text" name="message" id="message" placeholder="Message...">
	<input type="hidden" name="gameid" id="gameid" value=${redPlayer}>
      </div>
      <div>
	<input type="submit" value="Send">
      </div>
    </form>
  </div>

  <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>

  <script data-main="/js/game/index" src="/js/require.js"></script>

</body>
</html>
