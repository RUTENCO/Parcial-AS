import api from './api';

// GET Consultar inventario por ID de almacén
export const fetchInventory = async (warehouseId) => {
    try {
        // Hacemos GET a /inventory enviando el warehouseId como parámetro
        const response = await api.get(`/inventory?warehouseId=${warehouseId}`);
        return response.data;
    } catch (error) {
        console.error("Error buscando inventario", error);
        throw error;
    }
};

// POST Agregar producto al inventario
export const createInventory = async (inventoryData) => {
    try {
        // inventoryData debe coincidir con los campos de tu InventoryRequest en Java
        const response = await api.post('/inventory', inventoryData);
        return response.data;
    } catch (error) {
        console.error("Error creando producto", error);
        throw error;
    }
};