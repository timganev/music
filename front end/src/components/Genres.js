import React, { Component } from "react";
import ReactTable from "react-table";
import "react-table/react-table.css";
import { SERVER_URL } from "../constants.js";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Snackbar from "@material-ui/core/Snackbar";

class Genres extends Component {
  constructor(props) {
    super(props);
    this.state = { genres: [], open: false, message: "" };
  }

  componentDidMount() {
    this.fetchLists();
  }

  // Fetch all users
  fetchLists = () => {
    const token = sessionStorage.getItem("jwt");
    fetch(SERVER_URL + "api/genres", {
      headers: { Authorization: token }
    })
      .then(response => response.json())
      .then(responseData => {
        this.setState({
          genres: responseData._embedded.genres
        });
      })
      .catch(err => console.error(err));
  };

  customFilter = (filter, row) => {
    const id = filter.pivotId || filter.id;
    if (row[id] !== null && typeof row[id] === "string") {
      return row[id] !== undefined
        ? String(row[id].toLowerCase()).includes(filter.value.toLowerCase())
        : true;
    }
  };

  confirmDelete = link => {
    confirmAlert({
      message: "Are you sure to delete?",
      buttons: [
        {
          label: "Yes",
          onClick: () => this.onDelClick(link)
        },
        {
          label: "No"
        }
      ]
    });
  };

  // Delete list
  onDelClick = link => {
    const token = sessionStorage.getItem("jwt");
    fetch(link, {
      method: "DELETE",
      headers: { Authorization: token }
    })
      .then(res => {
        this.setState({ open: true, message: "Deleted" });
        this.fetchLists();
      })
      .catch(err => {
        this.setState({ open: true, message: "Error when deleting" });
        console.error(err);
      });
  };

  renderEditable = cellInfo => {
    return (
      <div
        style={{ backgroundColor: "#fafafa" }}
        contentEditable
        suppressContentEditableWarning
        onBlur={e => {
          const data = [...this.state.genres];
          data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
          this.setState({ genres: data });
        }}
        dangerouslySetInnerHTML={{
          __html: this.state.genres[cellInfo.index][cellInfo.column.id]
        }}
      />
    );
  };

  handleClose = (event, reason) => {
    this.setState({ open: false });
  };

  render() {
    const columns = [
      {
        Header: "name",
        accessor: "name"
      },

      {
        id: "savebutton",
        sortable: false,
        filterable: false,
        width: 100,
        accessor: "_links.self.href",
        Cell: ({ value, row }) => (
          <Button
            size="small"
            variant="flat"
            color="primary"
            onClick={() => {
              this.updatePlaylist(row, value);
            }}
          >
            Save
          </Button>
        )
      },
      {
        id: "delbutton",
        sortable: false,
        filterable: false,
        width: 100,
        accessor: "_links.self.href",
        Cell: ({ value }) => (
          <Button
            size="small"
            variant="flat"
            color="secondary"
            onClick={() => {
              this.confirmDelete(value);
            }}
          >
            Delete
          </Button>
        )
      }
    ];

    return (
      <div className="App">
        <Grid container>
          <Grid item />
          {/* <Grid item style={{ padding: 20 }} /> */}
        </Grid>

        <ReactTable
          data={this.state.genres}
          columns={columns}
          filterable={true}
          defaultFilterMethod={this.customFilter}
          pageSize={4}
          showPageSizeOptions={false}
          noDataText={"Plase Wait..."}
        />
        <Snackbar
          open={this.state.open}
          onClose={this.handleClose}
          autoHideDuration={1500}
          message={this.state.message}
        />
      </div>
    );
  }
}

export default Genres;
