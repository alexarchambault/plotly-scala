package plotly.doc;

// Defining these from Java code, so that scalac doesn't give
// those weird looking names under the hood.
public interface Plotly {
    void newPlot(String div, Object data, Object layout, Object other);
    void newPlot(String div, Object data, Object layout);
    void newPlot(String div, Object data);
}
