import React, { useState } from "react";
import { fetchInventory, createInventory } from "./services/inventoryService";

function App() {
  // Estados para manejar los datos
  const [warehouseId, setWarehouseId] = useState("");
  const [inventoryList, setInventoryList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // --- LÓGICA PARA EL GET (Punto 1) ---
  const handleSearch = async () => {
    if (!warehouseId) return;
    setLoading(true);
    setError(null);
    try {
      const data = await fetchInventory(warehouseId);
      console.log("Respuesta del Backend:", data); // ¡MIRA LA CONSOLA DEL NAVEGADOR!

      // HATEOAS: Spring suele devolver la lista dentro de "_embedded"
      // Si tu lista se llama diferente en el DTO, ajustaremos esto luego.
      if (data._embedded && data._embedded.inventoryDTOList) {
        setInventoryList(data._embedded.inventoryDTOList);
      } else if (Array.isArray(data)) {
        // Por si acaso no usaste HATEOAS correctamente y devuelve array directo
        setInventoryList(data);
      } else {
        // Fallback si la estructura es distinta
        setInventoryList([]);
        alert(
          "No se encontraron productos o la estructura es diferente (Revisar Consola)"
        );
      }
    } catch (err) {
      setError(
        "Error al consultar el almacén (Revisar si el backend está corriendo)"
      );
      setInventoryList([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
      <h1>Gestión de Inventario (Parcial)</h1>

      {/* SECCIÓN DE BÚSQUEDA */}
      <div
        style={{
          marginBottom: "20px",
          border: "1px solid #ccc",
          padding: "15px",
        }}
      >
        <h2>1. Consultar Inventario</h2>
        <label>ID del Almacén: </label>
        <input
          type="number"
          value={warehouseId}
          onChange={(e) => setWarehouseId(e.target.value)}
          placeholder="Ej: 1"
          style={{ marginRight: "10px", padding: "5px" }}
        />
        <button
          onClick={handleSearch}
          disabled={loading}
          style={{ padding: "5px 10px" }}
        >
          {loading ? "Buscando..." : "Consultar"}
        </button>
      </div>

      {/* MENSAJES DE ERROR */}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* TABLA DE RESULTADOS */}
      <h3>Resultados:</h3>
      {inventoryList.length > 0 ? (
        <table
          border="1"
          cellPadding="10"
          style={{ borderCollapse: "collapse", width: "100%" }}
        >
          <thead>
            <tr>
              <th>Nombre Producto</th>
              <th>Stock</th>
              <th>Precio</th>
              {/* Agrega aquí más columnas según tu InventoryDTO */}
            </tr>
          </thead>
          <tbody>
            {inventoryList.map((item, index) => (
              <tr key={index}>
                {/* Ajusta estos campos según los nombres en tu InventoryDTO */}
                <td>{item.productName || item.name || "Sin nombre"}</td>
                <td>{item.stock || item.quantity || 0}</td>
                <td>{item.price || 0}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No hay datos para mostrar.</p>
      )}
    </div>
  );
}

export default App;
