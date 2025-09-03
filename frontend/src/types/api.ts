// Equipment API types
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

export interface CreateEquipmentRequest {
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
}

// User API types
export interface User {
  id: number
  login: string
  email: string
  firstName: string
  lastName: string
  middleName?: string
  phone?: string
  position?: string
  department?: string
  active: boolean
  createdAt: string
  updatedAt: string
}

export interface CreateUserRequest {
  login: string
  password: string
  email: string
  firstName: string
  lastName: string
  middleName?: string
  phone?: string
  position?: string
  department?: string
}

// API Response types
export interface ApiResponse<T> {
  success: boolean
  data?: T
  error?: string
  message?: string
}

export interface PaginatedResponse<T> {
  items: T[]
  total: number
  page: number
  size: number
  totalPages: number
}

// Filter types
export interface EquipmentFilter {
  search?: string
  status?: string
  deviceTypeId?: number
  groupId?: number
  responsibleUserId?: number
}

export interface UserFilter {
  search?: string
  department?: string
  active?: boolean
}

// Auth types
export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  user: User
  roles: string[]
}

export interface AuthUser {
  id: number
  login: string
  email: string
  firstName: string
  lastName: string
  roles: string[]
}