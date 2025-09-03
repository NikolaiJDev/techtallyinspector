import { createSlice, PayloadAction } from '@reduxjs/toolkit'

export interface Equipment {
  id: number
  name: string
  serialNumber?: string
  inventoryNumber?: string
  deviceTypeId?: number
  groupId?: number
  location?: string
  responsibleUserId?: number
  status: 'active' | 'inactive' | 'maintenance' | 'disposed'
  purchaseDate?: string
  purchasePrice?: number
  warrantyEndDate?: string
  notes?: string
  createdAt: string
  updatedAt: string
}

export interface EquipmentFilter {
  search?: string
  status?: string
  deviceTypeId?: number
  groupId?: number
  responsibleUserId?: number
}

interface EquipmentState {
  items: Equipment[]
  total: number
  loading: boolean
  error: string | null
  filter: EquipmentFilter
  pagination: {
    page: number
    size: number
    sortBy: string
    sortDirection: 'asc' | 'desc'
  }
  selectedItem: Equipment | null
}

const initialState: EquipmentState = {
  items: [],
  total: 0,
  loading: false,
  error: null,
  filter: {},
  pagination: {
    page: 0,
    size: 20,
    sortBy: 'name',
    sortDirection: 'asc',
  },
  selectedItem: null,
}

const equipmentSlice = createSlice({
  name: 'equipment',
  initialState,
  reducers: {
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload
    },
    setItems: (state, action: PayloadAction<{ items: Equipment[]; total: number }>) => {
      state.items = action.payload.items
      state.total = action.payload.total
      state.loading = false
      state.error = null
    },
    setError: (state, action: PayloadAction<string>) => {
      state.error = action.payload
      state.loading = false
    },
    clearError: (state) => {
      state.error = null
    },
    setFilter: (state, action: PayloadAction<EquipmentFilter>) => {
      state.filter = { ...state.filter, ...action.payload }
      state.pagination.page = 0 // Reset to first page when filter changes
    },
    clearFilter: (state) => {
      state.filter = {}
    },
    setPagination: (state, action: PayloadAction<Partial<typeof initialState.pagination>>) => {
      state.pagination = { ...state.pagination, ...action.payload }
    },
    setSelectedItem: (state, action: PayloadAction<Equipment | null>) => {
      state.selectedItem = action.payload
    },
    addItem: (state, action: PayloadAction<Equipment>) => {
      state.items.unshift(action.payload)
      state.total += 1
    },
    updateItem: (state, action: PayloadAction<Equipment>) => {
      const index = state.items.findIndex(item => item.id === action.payload.id)
      if (index !== -1) {
        state.items[index] = action.payload
      }
      if (state.selectedItem?.id === action.payload.id) {
        state.selectedItem = action.payload
      }
    },
    removeItem: (state, action: PayloadAction<number>) => {
      state.items = state.items.filter(item => item.id !== action.payload)
      state.total -= 1
      if (state.selectedItem?.id === action.payload) {
        state.selectedItem = null
      }
    },
  },
})

export const {
  setLoading,
  setItems,
  setError,
  clearError,
  setFilter,
  clearFilter,
  setPagination,
  setSelectedItem,
  addItem,
  updateItem,
  removeItem,
} = equipmentSlice.actions

export default equipmentSlice.reducer