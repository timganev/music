import React from "react";
import ReactDOM from "react-dom";
import Button from "@material-ui/core/Button";

function getTime(time) {
  if (!isNaN(time)) {
    return (
      Math.floor(time / 60) + ":" + ("0" + Math.floor(time % 60)).slice(-2)
    );
  }
}

class Play extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedTrack: this.props.playlink,
      player: "stopped",
      currentTime: null,
      duration: null,
      playlink: this.props.playlink
    };
  }

  componentDidMount() {
    this.player.addEventListener("timeupdate", e => {
      this.setState({
        currentTime: e.target.currentTime,
        duration: e.target.duration
      });
    });
  }

  componentWillUnmount() {
    this.setState({ player: "stopped" });
    this.player.removeEventListener("timeupdate", () => {});
  }

  componentDidUpdate(prevProps, prevState) {
    if (this.props.playlink !== prevState.selectedTrack) {
      this.setState({ selectedTrack: this.props.playlink });

      this.player.src = this.props.playlink;
      this.player.play();
      this.setState({ player: "playing", duration: this.player.duration });
    }
    if (this.state.player !== prevState.player) {
      if (this.state.player === "paused") {
        this.player.pause();
      } else if (this.state.player === "stopped") {
        this.player.pause();
        this.player.currentTime = 0;
        this.setState({ selectedTrack: null });
      } else if (
        this.state.player === "playing" &&
        prevState.player === "paused"
      ) {
        this.player.play();
      }
    }
  }

  render() {
    const currentTime = getTime(this.state.currentTime);
    const duration = getTime(this.state.duration);

    return (
      <div>
        <div>
          {this.state.player === "paused" && (
            <Button
              variant="outlined"
              color="primary"
              onClick={() => this.setState({ player: "playing" })}
            >
              Play
            </Button>
          )}
          {this.state.player === "playing" && (
            <Button
              variant="outlined"
              color="primary"
              onClick={() => this.setState({ player: "paused" })}
            >
              Pause
            </Button>
          )}
          {/* {this.state.player === "playing" || this.state.player === "paused" ? (
            <button onClick={() => this.setState({ player: "stopped" })}>
              Stop
            </button>
          ) : (
            ""
          )} */}
        </div>

        {this.state.player === "playing" || this.state.player === "paused" ? (
          <p>
            {currentTime} / {duration}
          </p>
        ) : (
          ""
        )}
        <audio ref={ref => (this.player = ref)} />
      </div>
    );
  }
}

const rootElement = document.getElementById("root");
ReactDOM.render(<Play />, rootElement);

export default Play;
